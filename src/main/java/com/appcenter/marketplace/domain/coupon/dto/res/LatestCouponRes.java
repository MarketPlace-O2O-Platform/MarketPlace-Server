package com.appcenter.marketplace.domain.coupon.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)
public class LatestCouponRes {
    private final Long couponId;
    private final String couponName;
    private final Long marketId;
    private final String marketName;
    private String address;
    private final String thumbnail;
    private Boolean isAvailable;
    private Boolean isMemberIssued;
    private final LocalDateTime couponCreatedAt;

    // 최신 쿠폰 페이징 조회
    @QueryProjection
    public LatestCouponRes(Long couponId, String couponName, Long marketId, String marketName, String address, String thumbnail, Boolean isAvailable, Boolean isMemberIssued, LocalDateTime couponCreatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isAvailable = isAvailable;
        this.isMemberIssued = isMemberIssued;
        this.couponCreatedAt = couponCreatedAt;
    }

    // 최신 쿠폰 TOP 캐싱용 조회
    @QueryProjection
    public LatestCouponRes(Long couponId, String couponName, Long marketId, String marketName, String thumbnail, LocalDateTime couponCreatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.couponCreatedAt = couponCreatedAt;
    }
}
