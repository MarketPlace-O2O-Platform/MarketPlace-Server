package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponMarketResDto {
    private final Long marketId;
    private final Long couponId;
    private final String marketName;
    private final String marketDescription;
    private final String thumbnail;
    private final LocalDateTime modifiedAt;

    @QueryProjection
    public CouponMarketResDto(Long marketId, Long couponId, String marketName, String marketDescription, String thumbnail, LocalDateTime modifiedAt) {
        this.marketId = marketId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.thumbnail = thumbnail;
        this.modifiedAt = modifiedAt;
    }
}
