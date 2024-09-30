package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;

public interface CouponService {

    // 쿠폰 등록 메서드
    CouponResDto createCoupon(CouponReqDto couponReqDto, Long marketId);

    // 쿠폰 확인 메서드
    CouponResDto getCoupon(Long couponId);
}
