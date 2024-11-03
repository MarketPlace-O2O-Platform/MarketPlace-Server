package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssuedMemberCouponResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final Boolean used;

    @Builder
    private IssuedMemberCouponResDto(Long couponId, String couponName, String description, LocalDateTime deadLine, Boolean used) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.used = used;
    }

    public static IssuedMemberCouponResDto toDto(MemberCoupon memberCoupon) {
        return IssuedMemberCouponResDto.builder()
                .couponId(memberCoupon.getId())
                .couponName(memberCoupon.getCoupon().getName())
                .description(memberCoupon.getCoupon().getDescription())
                .deadLine(memberCoupon.getCoupon().getDeadLine())
                .used(memberCoupon.getIsUsed())
                .build();
    }

}
