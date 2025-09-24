package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;

import java.util.List;

public interface AdminCouponService {

    // 일반 쿠폰 관리
    CouponPageRes<CouponRes> getAllCoupons(Long couponId, Long marketId, Integer size);

    CouponRes getCoupon(Long couponId);

    CouponRes createCoupon(CouponReq couponReq, Long marketId);

    CouponRes updateCoupon(CouponReq couponReq, Long couponId);

    void updateCouponHidden(Long couponId);

    void softDeleteCoupon(Long couponId);

    void softDeleteCoupons(List<Long> couponIds);

    // 환급 쿠폰 관리
    CouponPageRes<PaybackRes> getAllPaybackCoupons(Long couponId, Long marketId, Integer size);

    PaybackRes getPaybackCoupon(Long couponId);

    PaybackRes createPaybackCoupon(PaybackReq paybackReq, Long marketId);

    PaybackRes updatePaybackCoupon(PaybackReq paybackReq, Long couponId);

    void updatePaybackCouponHidden(Long couponId);

    void softDeletePaybackCoupon(Long couponId);

    void softDeletePaybackCoupons(List<Long> couponIds);
}