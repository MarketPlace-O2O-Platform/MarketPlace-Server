package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssuedCouponRes {
    private final Long memberCouponId;
    private final Long couponId;
    private final String marketName;
    private final String thumbnail;
    private final String couponName;
    private final String description;
    private final Boolean used;
    // 증정형 / 환급형 구분용
    private final CouponType couponType;
    // 영수증 제출 여부
    private final Boolean isSubmit;

    private LocalDateTime deadLine;
    private Boolean expired;

    @QueryProjection
    @Builder
    public IssuedCouponRes(Long memberCouponId, Long couponId, String marketName, String thumbnail, String couponName, String description, LocalDateTime deadLine, Boolean used, Boolean expired, CouponType couponType, Boolean isSubmit) {
        this.memberCouponId = memberCouponId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.used = used;
        this.expired = expired;
        this.couponType = couponType;
        this.isSubmit = isSubmit;
    }

    @QueryProjection
    public IssuedCouponRes(Long memberCouponId, Long couponId, String marketName, String thumbnail, String couponName, String description, Boolean used, Boolean expired, CouponType couponType, Boolean isSubmit) {
        this.memberCouponId = memberCouponId;
        this.couponId = couponId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.couponName = couponName;
        this.description = description;
        this.used = used;
        this.expired = expired;
        this.couponType = couponType;
        this.isSubmit = isSubmit;
    }

    public static IssuedCouponRes toDto(MemberCoupon memberCoupon){
        return IssuedCouponRes.builder()
                .memberCouponId(memberCoupon.getId())
                .couponId(memberCoupon.getCoupon().getId())
                .marketName(memberCoupon.getCoupon().getMarket().getName())
                .thumbnail(memberCoupon.getCoupon().getMarket().getThumbnail())
                .couponName(memberCoupon.getCoupon().getName())
                .description(memberCoupon.getCoupon().getDescription())
                .deadLine(memberCoupon.getCoupon().getDeadLine())
                .used(memberCoupon.getIsUsed())
                .expired(memberCoupon.getIsExpired())
                .isSubmit(false)
                .build();
    }
}
