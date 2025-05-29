package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class TopLatestCouponRes {
    private final Long couponId;
    private final String couponName;
    private final Long marketId;
    private final String marketName;
    private final String thumbnail;
    private final LocalDateTime couponCreatedAt;

    // 최신 쿠폰 TOP 캐싱용 조회
    @QueryProjection
    public TopLatestCouponRes(Long couponId, String couponName, Long marketId, String marketName, String thumbnail, LocalDateTime couponCreatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.couponCreatedAt = couponCreatedAt;
    }
}
