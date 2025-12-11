package com.appcenter.marketplace.domain.tempMarket.service.impl;

import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.repository.TempMarketRepository;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_SEARCH_NAME_INVALID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TempMarketServiceImpl implements TempMarketService {
    private final TempMarketRepository tempMarketRepository;
    private final EntityManager entityManager;

    @Override
    public TempMarketPageRes<TempMarketRes> getMarketList(Long memberId, Long marketId, Integer size, String category) {
        List<TempMarketRes> marketResList;
        if(category == null){
            marketResList = tempMarketRepository.findMarketList(memberId, marketId, size);
        } else if(Major.exists(category)){
            marketResList = tempMarketRepository.findMarketListByCategory(memberId, marketId, size, category);
        }
        else throw new CustomException(CATEGORY_NOT_EXIST);
        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public TempMarketPageRes<TempMarketRes> getUpcomingNearMarketList(Long memberId, Long marketId, Integer cheerCount, Integer size) {
        List<TempMarketRes> marketResList = tempMarketRepository.findUpcomingMarketList(memberId, marketId, cheerCount, size);

        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public TempMarketPageRes<TempMarketRes> searchMarket(Long memberId, Long marketId, Integer size, String name) {
        if(name.length()<2)
            throw new CustomException(MARKET_SEARCH_NAME_INVALID);

        StringBuffer sb = new StringBuffer();

        // SELECT 절
        sb.append("SELECT ")
                .append("temp_market.id AS id, ")
                .append("temp_market.name AS name, ")
                .append("temp_market.description AS description, ")
                .append("temp_market.thumbnail AS thumbnail, ")
                .append("(cheer.id IS NOT NULL) AS isCheer ");



        // FROM 절
        sb.append("FROM temp_market ")
                .append("LEFT JOIN cheer ")
                .append("ON cheer.temp_market_id = temp_market.id ")
                .append("AND cheer.member_id = :memberId ");


        // WHERE 절
        sb.append("WHERE temp_market.is_hidden = FALSE ")
                .append("AND MATCH(temp_market.name) AGAINST(:name IN NATURAL LANGUAGE MODE) ");

        // marketId 조건 추가
        if (marketId != null) {
            sb.append("AND temp_market.id < :marketId ");
        }

        // ORDER BY 및 LIMIT
        sb.append("ORDER BY temp_market.id DESC ")
                .append("LIMIT :size;");

        Query query = entityManager.createNativeQuery(sb.toString())
                .setParameter("memberId", memberId)
                .setParameter("name", name)
                .setParameter("size", size+1);

        // marketId가 null이 아닐 경우 두 번째 파라미터 설정
        if (marketId != null) {
            query.setParameter("marketId", marketId);
        }

        // qlrm을 사용한 결과 매핑
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<TempMarketRes> marketResDtoList= resultMapper.list(query, TempMarketRes.class);

        return checkNextPageAndReturn(marketResDtoList, size);
    }

    private <T> TempMarketPageRes<T> checkNextPageAndReturn(List<T> marketResDtoList, Integer size){
        boolean hasNext = marketResDtoList.size() > size;

        if(hasNext){
            marketResDtoList = marketResDtoList.subList(0, size);
        }

        return new TempMarketPageRes<>(marketResDtoList, hasNext);
    }
}
