package com.appcenter.marketplace.domain.beta.dto.res;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BetaCouponRes {
    private final Long betaCouponId;
    private final String marketName;
    private final String couponName;
    private final String couponDetail;
    private final String image;
    private final Boolean isUsed;

    @QueryProjection
    public BetaCouponRes(Long betaCouponId, String marketName, String couponName, String couponDetail, String image, Boolean isUsed) {
        this.betaCouponId = betaCouponId;
        this.marketName = marketName;
        this.couponName = couponName;
        this.couponDetail = couponDetail;
        this.image = image;
        this.isUsed = isUsed;
    }
}
