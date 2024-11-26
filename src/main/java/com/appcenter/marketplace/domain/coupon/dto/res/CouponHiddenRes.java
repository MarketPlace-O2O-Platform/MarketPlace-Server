package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponHiddenRes {
    private final Long couponId;
    private final Boolean hidden;

    @Builder
    public CouponHiddenRes(Long couponId, Boolean hidden) {
        this.couponId = couponId;
        this.hidden = hidden;
    }

    public static CouponHiddenRes toDto(Coupon coupon) {
        return CouponHiddenRes.builder()
                .couponId(coupon.getId())
                .hidden(coupon.getIsHidden())
                .build();
    }
}
