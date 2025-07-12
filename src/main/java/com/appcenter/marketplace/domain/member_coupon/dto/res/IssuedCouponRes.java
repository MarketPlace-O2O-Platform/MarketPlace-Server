package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssuedCouponRes {
    private final Long memberCouponId;
    private final Long couponId;
    private final String thumbnail;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final Boolean used;
    private final Boolean expired;

    @QueryProjection
    @Builder
    public IssuedCouponRes(Long memberCouponId, Long couponId,String thumbnail, String couponName, String description, LocalDateTime deadLine, Boolean used, Boolean expired) {
        this.memberCouponId = memberCouponId;
        this.couponId = couponId;
        this.thumbnail = thumbnail;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.used = used;
        this.expired = expired;
    }

    public static IssuedCouponRes toDto(MemberCoupon memberCoupon){
        return IssuedCouponRes.builder()
                .memberCouponId(memberCoupon.getId())
                .couponId(memberCoupon.getId())
                .thumbnail(memberCoupon.getCoupon().getMarket().getThumbnail())
                .couponName(memberCoupon.getCoupon().getName())
                .description(memberCoupon.getCoupon().getDescription())
                .deadLine(memberCoupon.getCoupon().getDeadLine())
                .used(memberCoupon.getIsUsed())
                .expired(memberCoupon.getIsExpired())
                .build();
    }
}
