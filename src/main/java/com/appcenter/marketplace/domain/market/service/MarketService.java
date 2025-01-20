package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketService {

    MarketDetailsRes getMarketDetails(Long marketId);

    MarketPageRes<MarketRes> getMarketPage(Long memberId, Long marketId, Integer size, String major);

    MarketPageRes<MarketRes> getMarketPageByAddress(Long memberId, Long marketId, Integer size, String major, String address);

    MarketPageRes<MarketRes> getMyFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    MarketPageRes<MarketRes> searchMarket(Long marketId, Integer size, String name);

//    MarketPageRes<MarketRes> getFavoriteMarketPage(Long memberId, Long marketId, Long count, Integer size);
//
//    List<MarketRes> getTopFavoriteMarkets(Long memberId, Integer size);

    List<TopLatestCouponRes> getTopLatestCoupons(Long memberId, Integer size);

    MarketPageRes<LatestCouponRes> getLatestCouponPage(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);



}
