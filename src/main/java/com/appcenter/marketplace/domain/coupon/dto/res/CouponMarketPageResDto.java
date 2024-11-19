package com.appcenter.marketplace.domain.coupon.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class CouponMarketPageResDto {
    private final List<CouponMarketResDto> couponMarkets;
    private final boolean hasNext;

    public CouponMarketPageResDto(List<CouponMarketResDto> couponMarkets, boolean hasNext) {
        this.couponMarkets = couponMarkets;
        this.hasNext = hasNext;
    }
}
