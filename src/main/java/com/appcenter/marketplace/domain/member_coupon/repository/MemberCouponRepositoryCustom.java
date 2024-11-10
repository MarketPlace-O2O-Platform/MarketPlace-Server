package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;

import java.util.List;

public interface MemberCouponRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long couponId);

    List<IssuedMemberCouponResDto> findIssuedCouponResDtoByMemberId(Long memberId);
    List<IssuedMemberCouponResDto> findExpiredCouponResDtoByMemberId(Long memberId);
    List<IssuedMemberCouponResDto> findUsedMemberCouponResDtoByMemberId(Long memberId);
}
