package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.market.dto.res.*;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;


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

    // 자신이 찜한 매장 리스트 조회
    @Override
    public MarketPageRes<MyFavoriteMarketRes> getMyFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size) {
        List<MyFavoriteMarketRes> marketResDtoList = marketRepository.findMyFavoriteMarketList(memberId, lastModifiedAt, size);

        return checkNextPageAndReturn(marketResDtoList, size);
    }

    // 찜 수가 가장 많은 매장 더보기 조회
    @Override
    public MarketPageRes<FavoriteMarketRes> getFavoriteMarketPage(Long memberId, Long count, Integer size) {
        List<FavoriteMarketRes> favoriteMarketResList = marketRepository.findFavoriteMarketList(memberId, count, size);

        return checkNextPageAndReturn(favoriteMarketResList, size);
    }

    // 찜 수가 가장 많은 매장 TOP 조회
    @Override
    public List<TopFavoriteMarketRes> getTopFavoriteMarkets(Integer size) {
        return marketRepository.findTopFavoriteMarkets(size);
    }


    // 최신 등록 쿠폰 TOP 조회
    @Override
    public List<TopLatestCouponRes> getTopLatestCoupons(Integer size) {
        return marketRepository.findTopLatestCoupons(size);
    }

    // 최신 등록 쿠폰의 매장 더보기 조회
    @Override
    public MarketPageRes<LatestCouponRes> getLatestCouponPage(LocalDateTime lastModifiedAt, Long lastCouponId, Integer size) {
        List<LatestCouponRes> resDtoList = marketRepository.findLatestCouponList(lastModifiedAt, lastCouponId, size);

        if (resDtoList.isEmpty())
            throw new CustomException(MARKET_NOT_EXIST);

        return checkNextPageAndReturn(resDtoList, size);
    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    public List<TopClosingCouponRes> getTopClosingCoupons(Integer size) {
        return marketRepository.findTopClosingCoupons(size);
    }

    private <T> MarketPageRes<T> checkNextPageAndReturn(List<T> marketResDtoList, Integer size) {
        boolean hasNext = false;

        // 가져온 갯수가 페이지 사이즈보다 많으면 다음 페이지가 있는 것이고, 사이즈에 맞게 조정한다.
        if (marketResDtoList.size() > size) {
            hasNext = true;
            marketResDtoList.remove(size.intValue());
        }

        return new MarketPageRes<>(marketResDtoList, hasNext);
    }

}
