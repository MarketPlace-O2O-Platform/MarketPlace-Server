package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
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

    public static CouponHandleRes toDto(MemberPayback memberPayback) {
        return CouponHandleRes.builder()
                .couponId(memberPayback.getId())
                .isUsed(memberPayback.getIsPayback())
                .build();
    }
}
