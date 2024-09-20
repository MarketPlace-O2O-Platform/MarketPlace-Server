package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.domain.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;

public interface CouponService {

    // 쿠폰 등록 메서드
    Coupon createCoupon(CouponReqDto couponReqDto, Long marketId);

}
