package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCouponUpdateResDto {
    private final Long couponId;
    private final Boolean isUsed;

    @Builder
    private MemberCouponUpdateResDto(Long couponId, Boolean isUsed) {
        this.couponId = couponId;
        this.isUsed = isUsed;
    }

    public static MemberCouponUpdateResDto toDto(MemberCoupon memberCoupon) {
        return MemberCouponUpdateResDto.builder()
                .couponId(memberCoupon.getId())
                .isUsed(memberCoupon.getIsUsed())
                .build();
    }
}
