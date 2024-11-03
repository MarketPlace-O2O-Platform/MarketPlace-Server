package com.appcenter.marketplace.domain.member_coupon.repository;

public interface MemberCouponRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long couponId);
}
