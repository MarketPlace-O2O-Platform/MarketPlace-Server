package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponClosingTopResDto {
    private final Long marketId;
    private final Long couponId;
    private final String marketName;
    private final String couponName;
    private final LocalDateTime deadline;
    private final String thumbnail;

    @QueryProjection
    public CouponClosingTopResDto(Long marketId, Long couponId, String marketName, String couponName, LocalDateTime deadline, String thumbnail) {
        this.marketId = marketId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.couponName = couponName;
        this.deadline = deadline;
        this.thumbnail = thumbnail;
    }
}
