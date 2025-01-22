package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;

public interface CouponOwnerService {

    // 쿠폰 등록 메서드
    CouponRes createCoupon(CouponReq couponReq, Long marketId);

    // 쿠폰 확인 메서드
    CouponRes getCoupon(Long couponId);

    // 매장별 전체 쿠폰 확인 메서드
    CouponPageRes<CouponRes> getCouponList(Long marketId, Long CouponId, Integer size);

    // 쿠폰 내용 수정 메서드
    CouponRes updateCoupon(CouponReq couponReq, Long couponId);

    // 쿠폰 숨김 처리 메서드
    void updateCouponHidden(Long couponId);

    // 쿠폰 삭제 메서드
    void deleteCoupon(Long couponId);
}
