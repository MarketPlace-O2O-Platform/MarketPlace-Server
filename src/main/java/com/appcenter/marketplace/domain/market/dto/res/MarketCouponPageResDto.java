package com.appcenter.marketplace.domain.market.dto.res;


import lombok.Getter;

import java.util.List;

@Getter
public class MarketCouponPageResDto {
    private final List<MarketCouponResDto> couponMarkets;
    private final boolean hasNext;

    public MarketCouponPageResDto(List<MarketCouponResDto> couponMarkets, boolean hasNext) {
        this.couponMarkets = couponMarkets;
        this.hasNext = hasNext;
    }
}
