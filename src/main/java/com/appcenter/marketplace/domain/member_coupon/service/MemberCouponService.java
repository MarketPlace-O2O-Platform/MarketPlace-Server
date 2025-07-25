package com.appcenter.marketplace.domain.member_coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;

import java.util.List;


public interface MemberCouponService {
    void issuedCoupon(Long memberId, Long couponId);
    CouponPageRes<IssuedCouponRes> getMemberCouponList(Long memberId, MemberCouponType type, Long couponId, Integer size);
    CouponHandleRes updateCoupon(Long memberCouponId);
    void hardDeleteCoupon(List<Long> couponIds);

    void check3DaysCoupons();
    void checkExpiredCoupons();
}
