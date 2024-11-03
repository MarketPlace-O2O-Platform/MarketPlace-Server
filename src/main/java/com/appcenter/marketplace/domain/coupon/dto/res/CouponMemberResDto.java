package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponMemberResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;

    @Builder
    private CouponMemberResDto(Long couponId, String couponName, String description, LocalDateTime deadLine) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
    }

    public static CouponMemberResDto toDto(Coupon coupon) {
        return CouponMemberResDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .description(coupon.getDescription())
                .deadLine(coupon.getDeadLine())
                .build();
    }
}
