package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketRepositoryCustom {
    List<MarketDetailsRes> findMarketDetailList(Long marketId);

    List<MarketRes> findMarketList(Long memberId, Long marketId, Integer size);

    List<MarketRes> findMarketListByCategory(Long memberId, Long marketId, Integer size, String major);

    List<MarketRes> findMarketListByAddress(Long memberId, Long marketId, Long localId, Integer size);

    List<MarketRes> findMarketListByAddressAndCategory(Long memberId, Long marketId, Long localId, Integer size, String major);

    List<MarketRes> findMyFavoriteMarketList(Long memberId, LocalDateTime lastModifiedAt, Integer size);

//    List<MarketRes> findFavoriteMarketList(Long memberId,Long marketId, Long count, Integer size);
//
//    // 찜 수가 가장 많은 매장 Top 조회
//    List<MarketRes> findTopFavoriteMarkets(Long memberId, Integer size);

    List<TopLatestCouponRes> findTopLatestCoupons(Long memberId, Integer size);

    List<LatestCouponRes> findLatestCouponList(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

    List<TopClosingCouponRes> findTopClosingCoupons(Integer size);
}

