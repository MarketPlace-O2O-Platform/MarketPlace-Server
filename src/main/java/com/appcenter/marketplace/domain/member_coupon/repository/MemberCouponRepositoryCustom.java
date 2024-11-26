package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;

import java.util.List;

public interface MemberCouponRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long couponId);

    List<IssuedCouponRes> findIssuedCouponResDtoByMemberId(Long memberId);
    List<IssuedCouponRes> findExpiredCouponResDtoByMemberId(Long memberId);
    List<IssuedCouponRes> findUsedMemberCouponResDtoByMemberId(Long memberId);
}
