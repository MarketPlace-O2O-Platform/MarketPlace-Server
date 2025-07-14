package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;

import java.util.List;

public interface MemberCouponRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long couponId);

    List<IssuedCouponRes> findIssuedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    List<IssuedCouponRes> findEndedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    void check3DaysCoupons();
    void checkExpiredCoupons();
}
