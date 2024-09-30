package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CouponHiddenResDto {
    private final Long couponId;
    private final Boolean hidden;

    @Builder
    public CouponHiddenResDto( Long couponId, Boolean hidden) {
        this.couponId = couponId;
        this.hidden = hidden;
    }

    public static CouponHiddenResDto toDto(Coupon coupon) {
        return CouponHiddenResDto.builder()
                .couponId(coupon.getId())
                .hidden(coupon.getIsHidden())
                .build();
    }
}
