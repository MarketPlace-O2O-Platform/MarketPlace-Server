package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;

import java.util.List;

public interface CouponOwnerService {

    // 쿠폰 등록 메서드
    CouponRes createCoupon(CouponReq couponReq, Long marketId);

    // 쿠폰 확인 메서드
    CouponRes getCoupon(Long couponId);

    // 매장별 전체 쿠폰 확인 메서드
    List<CouponRes> getCouponList(Long marketId);

    // 쿠폰 내용 수정 메서드
    CouponRes updateCoupon(CouponUpdateReq couponUpdateReq, Long couponId);

    // 쿠폰 숨김 처리 메서드
    CouponHiddenRes updateCouponHidden(Long couponId);

    // 쿠폰 삭제 메서드
    void deleteCoupon(Long couponId);
}
