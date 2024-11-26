package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LatestCouponRes {
    private final Long marketId;
    private final Long couponId;
    private final String marketName;
    private final String marketDescription;
    private final String address;
    private final String thumbnail;
    private final LocalDateTime modifiedAt;

    @QueryProjection
    public LatestCouponRes(Long marketId, Long couponId, String marketName, String marketDescription, String address, String thumbnail, LocalDateTime modifiedAt) {
        this.marketId = marketId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.modifiedAt = modifiedAt;
    }
}
