package com.appcenter.marketplace.domain.member_coupon.service;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;

import java.util.List;

public interface MemberCouponService {
    void issuedCoupon(Long memberId, Long couponId);
    List<IssuedCouponRes> getMemberCouponList(Long memberId);
    List<IssuedCouponRes> getExpiredMemberCouponList(Long memberId);
    List<IssuedCouponRes> getUsedMemberCouponList(Long memberId);

    CouponHandleRes updateCoupon(Long memberCouponId);
    IssuedCouponRes getMemberCoupon(Long memberCouponId);
}
