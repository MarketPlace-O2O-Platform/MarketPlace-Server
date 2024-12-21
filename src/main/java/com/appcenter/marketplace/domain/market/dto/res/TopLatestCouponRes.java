package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TopLatestCouponRes {
    private final Long marketId;
    private final Long couponId;
    private final String marketName;
    private final String couponName;
    private final String thumbnail;
    private final Boolean isFavorite;

    @QueryProjection
    public TopLatestCouponRes(Long marketId, Long couponId, String marketName, String couponName, String thumbnail, Boolean isFavorite) {
        this.marketId = marketId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.couponName = couponName;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
    }
}
