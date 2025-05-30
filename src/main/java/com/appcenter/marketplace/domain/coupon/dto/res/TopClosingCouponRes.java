package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class TopClosingCouponRes {
    private final Long couponId;
    private final String couponName;
    private final LocalDateTime deadline;
    private final Long marketId;
    private final String marketName;
    private final String thumbnail;

    @QueryProjection
    public TopClosingCouponRes(Long couponId, String couponName, LocalDateTime deadline, Long marketId, String marketName, String thumbnail) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.deadline = deadline;
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
    }
}
