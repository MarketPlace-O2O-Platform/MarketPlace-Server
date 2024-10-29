package com.appcenter.marketplace.domain.coupon.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CouponListResDto {
    private final List<CouponResDto> couponList;

    @Builder
    public CouponListResDto(List<CouponResDto> couponList){
        this.couponList = couponList;
    }

    public static CouponListResDto toDto(List<CouponResDto> couponList){
       return CouponListResDto.builder().couponList(couponList).build();
    }
}
