package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.local.repository.LocalRepository;
import com.appcenter.marketplace.domain.market.dto.res.*;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringTokenizer;

import static com.appcenter.marketplace.global.common.StatusCode.*;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;
    private final LocalRepository localRepository;
    private final EntityManager entityManager;


    // 매장 상세 정보 조회
    @Override
    public MarketDetailsRes getMarketDetails(Long marketId) {
        List<MarketDetailsRes> marketDetailsResList = marketRepository.findMarketDetailList(marketId);

        if (marketDetailsResList.isEmpty())
            throw new CustomException(StatusCode.MARKET_NOT_EXIST);

        return marketDetailsResList.get(0);
    }

    // 매장 페이지 조회
    @Override
    public MarketPageRes<MarketRes> getMarketPage(Long memberId, Long marketId, Integer size, String major) {
        List<MarketRes> marketResList;
        if (major == null) {
            marketResList = marketRepository.findMarketList(memberId, marketId, size);
        } else if (Major.exists(major)) {
            marketResList = marketRepository.findMarketListByCategory(memberId, marketId, size, major);
        } else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public MarketPageRes<MarketRes> getMarketPageByAddress(Long memberId, Long marketId, Integer size, String major, String address) {
        StringTokenizer st= new StringTokenizer(address);

        // 두 개 이상의 단어가 있을 경우만 처리
        if (st.countTokens() < 2) {
            throw new CustomException(ADDRESS_INVALID);
        }

        Local local=localRepository.findByAdress(st.nextToken(),st.nextToken());

        List<MarketRes> marketResList;
        if (major == null) {
            marketResList = marketRepository.findMarketListByAddress(memberId, marketId, local.getId(), size);
        } else if (Major.exists(major)) {
            marketResList = marketRepository.findMarketListByAddressAndCategory(memberId, marketId,local.getId(), size, major);
        } else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public MarketPageRes<MarketRes> searchMarket(Long marketId, Integer size, String name) {
        if(name.length()<2)
            throw new CustomException(MARKET_SEARCH_NAME_INVALID);

        StringBuffer sb = new StringBuffer();

        // SELECT 절
        sb.append("SELECT ")
                .append("market.id AS id, ")
                .append("market.name AS name, ")
                .append("market.description AS description, ")
                .append("CONCAT(metropolitan_government.name, ' ', local_government.name) AS location, ")
                .append("market.thumbnail AS thumbnail, ")
                .append("(new_coupon.id IS NOT NULL) AS is_new_coupon, ")
                .append("(closing_coupon.id IS NOT NULL) AS is_closing_soon, ")
                .append("category.major AS major, ")
                .append("market.order_no AS orderNo ");

        // FROM 절
        sb.append("FROM market ")
                .append("LEFT JOIN coupon AS new_coupon ")
                .append("ON new_coupon.market_id = market.id ")
                .append("AND new_coupon.is_deleted = FALSE ")
                .append("AND new_coupon.is_hidden = FALSE ")
                .append("AND new_coupon.created_at >= NOW() - INTERVAL 7 DAY ")
                .append("LEFT JOIN coupon AS closing_coupon ")
                .append("ON closing_coupon.market_id = market.id ")
                .append("AND closing_coupon.is_deleted = FALSE ")
                .append("AND closing_coupon.is_hidden = FALSE ")
                .append("AND closing_coupon.dead_line BETWEEN NOW() AND NOW() + INTERVAL 7 DAY ")
                .append("INNER JOIN local_government ")
                .append("ON market.local_government_id = local_government.id ")
                .append("INNER JOIN metropolitan_government ")
                .append("ON local_government.metropolitan_government_id = metropolitan_government.id ")
                .append("INNER JOIN category ")
                .append("ON market.category_id = category.id ");

        // WHERE 절
        sb.append("WHERE MATCH(market.name) AGAINST(:name IN NATURAL LANGUAGE MODE) ");

        // marketId 조건 추가
        if (marketId != null) {
            sb.append("AND market.id < :marketId ");
        }

        // ORDER BY 및 LIMIT
        sb.append("ORDER BY market.id DESC ")
                .append("LIMIT :size;");

        Query query = entityManager.createNativeQuery(sb.toString())
                .setParameter("name", name)
                .setParameter("size", size+1);

        // marketId가 null이 아닐 경우 두 번째 파라미터 설정
        if (marketId != null) {
            query.setParameter("marketId", marketId);
        }

        // qlrm을 사용한 결과 매핑
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<MarketRes> marketResDtoList= resultMapper.list(query, MarketRes.class);

        return checkNextPageAndReturn(marketResDtoList, size);
    }

    // 자신이 찜한 매장 리스트 조회
    @Override
    public MarketPageRes<MarketRes> getMyFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size) {
        List<MarketRes> marketResDtoList = marketRepository.findMyFavoriteMarketList(memberId, lastModifiedAt, size);

        return checkNextPageAndReturn(marketResDtoList, size);
    }

//    // 찜 수가 가장 많은 매장 더보기 조회
//    @Override
//    public MarketPageRes<MarketRes> getFavoriteMarketPage(Long memberId, Long marketId, Long count, Integer size) {
//        List<MarketRes> favoriteMarketResList = marketRepository.findFavoriteMarketList(memberId, marketId, count, size);
//
//        return checkNextPageAndReturn(favoriteMarketResList, size);
//    }
//
//    // 찜 수가 가장 많은 매장 TOP 조회
//    @Override
//    public List<MarketRes> getTopFavoriteMarkets(Long memberId, Integer size) {
//        return marketRepository.findTopFavoriteMarkets(memberId, size);
//    }

    private <T> MarketPageRes<T> checkNextPageAndReturn(List<T> marketResDtoList, Integer size) {
        boolean hasNext = marketResDtoList.size() > size;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이고, 사이즈에 맞게 조정한다.
        if (hasNext) {
            marketResDtoList = marketResDtoList.subList(0,size);
        }

        return new MarketPageRes<>(marketResDtoList, hasNext);
    }

}
