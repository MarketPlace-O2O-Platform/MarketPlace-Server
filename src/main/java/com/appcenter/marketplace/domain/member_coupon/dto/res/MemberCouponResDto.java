package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCouponResDto {
    private final Long memberCouponId;

    @Builder
    public MemberCouponResDto(Long memberCouponId) {
        this.memberCouponId = memberCouponId;
    }

    public static MemberCouponResDto toDto(MemberCoupon memberCoupon) {
        return MemberCouponResDto.builder()
                .memberCouponId(memberCoupon.getId())
                .build();
    }
}
