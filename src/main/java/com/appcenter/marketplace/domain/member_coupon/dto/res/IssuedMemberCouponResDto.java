package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssuedMemberCouponResDto {
    private final Long memberCouponId;
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final Boolean used;

    @QueryProjection
    @Builder
    public IssuedMemberCouponResDto(Long memberCouponId, Long couponId, String couponName, String description, LocalDateTime deadLine, Boolean used) {
        this.memberCouponId = memberCouponId;
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.used = used;
    }

    public static IssuedMemberCouponResDto toDto(MemberCoupon memberCoupon){
        return IssuedMemberCouponResDto.builder()
                .memberCouponId(memberCoupon.getId())
                .couponId(memberCoupon.getId())
                .couponName(memberCoupon.getCoupon().getName())
                .description(memberCoupon.getCoupon().getDescription())
                .deadLine(memberCoupon.getCoupon().getDeadLine())
                .used(memberCoupon.getIsUsed())
                .build();
    }
}
