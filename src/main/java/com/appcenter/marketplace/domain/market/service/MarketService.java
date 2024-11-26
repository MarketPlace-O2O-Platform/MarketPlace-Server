package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    MarketPageResDto getMarketPage(Long marketId, Integer size, String major);

    MarketPageResDto getMemberFavoriteMarketPage(Long memberId, Long marketId, Integer size);

    MarketPageResDto getTopFavoriteMarketPage(Long marketId, Integer size);

    List<CouponLatestTopResDto> getCouponLatestTop(Integer size);

    MarketCouponPageResDto getLatestCouponList(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<CouponClosingTopResDto> getCouponClosingTop(Integer size);

}
