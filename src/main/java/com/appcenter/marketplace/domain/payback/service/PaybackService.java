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

    // 환급 쿠폰 숨김 처리 메서드
    void updateCouponHidden(Long couponId);

    // 환급 쿠폰 전체 확인 메서드 (관리자용)
    CouponPageRes<PaybackRes> getCouponListForAdmin(Long marketId, Long couponId, Integer size);

    // 환급 쿠폰 전체 확인 메서드 (유저용 - isHidden False)
    CouponPageRes<PaybackRes> getCouponListForMembers(Long marketId, Long couponId, Integer size);

}
