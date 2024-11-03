package com.appcenter.marketplace.domain.coupon.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CouponListResDto {
    private final List<CouponMemberResDto> couponList;

    @Builder
    public CouponListResDto(List<CouponMemberResDto> couponList){
        this.couponList = couponList;
    }

    public static CouponListResDto toDto(List<CouponMemberResDto> couponList){
       return CouponListResDto.builder().couponList(couponList).build();
    }
}
