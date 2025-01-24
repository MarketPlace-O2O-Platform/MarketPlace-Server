package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PopularCouponRes {
    private final Long couponId;
    private final String couponName;
    private final Long marketId;
    private final String marketName;
    private final String address;
    private final String thumbnail;
    private final Boolean isAvailable;
    private final Boolean isMemberIssued;
    private final Long issuedCount;

    @QueryProjection
    public PopularCouponRes(Long couponId, String couponName, Long marketId, String marketName, String address, String thumbnail, Boolean isAvailable, Boolean isMemberIssued, Long issuedCount) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isAvailable = isAvailable;
        this.isMemberIssued = isMemberIssued;
        this.issuedCount = issuedCount;
    }
}
