package com.appcenter.marketplace.domain.member_payback.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;

import java.util.List;

public interface MemberPaybackRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long paybackId);
    List<IssuedCouponRes> findIssuedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    List<IssuedCouponRes> findEndedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    void check3DaysCoupons();
}
