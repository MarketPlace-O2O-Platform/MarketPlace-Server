package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponHandleRes {
    private final Long couponId;
    private final Boolean isUsed;

    @Builder
    private CouponHandleRes(Long couponId, Boolean isUsed) {
        this.couponId = couponId;
        this.isUsed = isUsed;
    }

    public static CouponHandleRes toDto(MemberCoupon memberCoupon) {
        return CouponHandleRes.builder()
                .couponId(memberCoupon.getId())
                .isUsed(memberCoupon.getIsUsed())
                .build();
    }
}
