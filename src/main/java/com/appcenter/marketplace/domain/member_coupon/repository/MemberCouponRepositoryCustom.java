package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;

import java.util.List;

public interface MemberCouponRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long couponId);

    List<IssuedMemberCouponResDto> findIssuedCouponsByMemberId(Long memberId);
    List<IssuedMemberCouponResDto> findExpiredCouponsByMemberId(Long memberId);

    List<IssuedMemberCouponResDto> findUsedCouponsByMemberId(Long memberId);
}
