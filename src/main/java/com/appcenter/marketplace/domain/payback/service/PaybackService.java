package com.appcenter.marketplace.domain.payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;


// 관리자용
public interface PaybackService {

    // 환급 쿠폰 등록 메서드
    PaybackRes createCoupon(PaybackReq req, Long marketId);

    // 환급 쿠폰 수정 메서드
    PaybackRes updateCoupon(PaybackReq req, Long couponId);
}
