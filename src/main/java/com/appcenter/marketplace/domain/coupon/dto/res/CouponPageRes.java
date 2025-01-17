package com.appcenter.marketplace.domain.coupon.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class CouponPageRes<T> {
    private final List<T> couponResDtos;
    private final boolean hasNext;

    public CouponPageRes(List<T> couponResDtos, boolean hasNext){
        this.couponResDtos = couponResDtos;
        this.hasNext = hasNext;
    }
}
