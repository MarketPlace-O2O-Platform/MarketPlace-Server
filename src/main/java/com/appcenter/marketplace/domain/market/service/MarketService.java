package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    MarketPageResDto<MarketResDto> getMarketPage(Long memberId, Long marketId, Integer size, String major);

    MarketPageResDto<MyFavoriteMarketResDto> getMyFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    MarketPageResDto<FavoriteMarketResDto> getFavoriteMarketPage(Long memberId, Long count, Integer size);
    List<TopFavoriteMarketResDto> getTopFavoriteMarkets(Integer size);

    List<CouponLatestTopResDto> getTopLatestCoupons(Integer size);

    MarketCouponPageResDto getLatestCouponPage(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<CouponClosingTopResDto> getTopClosingCoupons(Integer size);


}
