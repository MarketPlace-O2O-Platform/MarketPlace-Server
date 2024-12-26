package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LatestCouponRes {
    private final Long marketId;
    private final String marketName;
    private final String marketDescription;
    private final String address;
    private final String thumbnail;
    private final Boolean isFavorite;
    private final Boolean isNewCoupon;
    private final LocalDateTime createdAt;

    @QueryProjection
    public LatestCouponRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, LocalDateTime createdAt) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.createdAt = createdAt;
    }
}
