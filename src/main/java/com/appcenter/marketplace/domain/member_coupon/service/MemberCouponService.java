package com.appcenter.marketplace.domain.member_coupon.service;

import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;

public interface MemberCouponService {
    MemberCouponResDto issuedCoupon(Long memberId, Long couponId);
}
