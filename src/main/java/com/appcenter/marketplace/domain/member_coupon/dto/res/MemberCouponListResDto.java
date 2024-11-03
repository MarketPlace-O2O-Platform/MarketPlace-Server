package com.appcenter.marketplace.domain.member_coupon.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberCouponListResDto {
    private List<IssuedMemberCouponResDto> IssuedmemberCouponList;

    @Builder
    private MemberCouponListResDto(List<IssuedMemberCouponResDto> IssuedmemberCouponList) {
        this.IssuedmemberCouponList = IssuedmemberCouponList;
    }

    public static MemberCouponListResDto toDto(List<IssuedMemberCouponResDto> IssuedmemberCouponLi) {
        return MemberCouponListResDto.builder().IssuedmemberCouponList(IssuedmemberCouponLi).build();
    }
}
