package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketService {

    MarketDetailsRes getMarketDetails(Long marketId);

    MarketPageRes<MarketRes> getMarketPage(Long memberId, Long marketId, Integer size, String major);

    MarketPageRes<MyFavoriteMarketRes> getMyFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    MarketPageRes<FavoriteMarketRes> getFavoriteMarketPage(Long memberId, Long marketId, Long count, Integer size);
    List<TopFavoriteMarketRes> getTopFavoriteMarkets(Integer size);

    List<TopLatestCouponRes> getTopLatestCoupons(Integer size);

    MarketPageRes<LatestCouponRes> getLatestCouponPage(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<TopClosingCouponRes> getTopClosingCoupons(Integer size);


}
