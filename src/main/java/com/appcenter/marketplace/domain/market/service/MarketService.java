package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    MarketPageResDto<MarketResDto> getMarketPage(Long memberId, Long marketId, Integer size, String major);

    MarketPageResDto<MyFavoriteMarketResDto> getMemberFavoriteMarketPage(Long memberId, LocalDateTime lastModifiedAt, Integer size);


    MarketPageResDto<FavoriteMarketResDto> getFavoriteMarketPage(Long memberId, Long count, Integer size);
    List<TopFavoriteMarketResDto> getTopFavoriteMarkets(Integer size);

    List<CouponLatestTopResDto> getCouponLatestTop(Integer size);

    MarketCouponPageResDto getLatestCouponList(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<CouponClosingTopResDto> getCouponClosingTop(Integer size);


}
