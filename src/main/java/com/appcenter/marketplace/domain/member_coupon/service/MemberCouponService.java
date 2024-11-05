package com.appcenter.marketplace.domain.member_coupon.service;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponUpdateResDto;

import java.util.List;

public interface MemberCouponService {
    void issuedCoupon(Long memberId, Long couponId);
    List<IssuedMemberCouponResDto> getMemberCouponList(Long memberId);
    List<IssuedMemberCouponResDto> getExpiredMemberCouponList(Long memberId);
    List<IssuedMemberCouponResDto> getUsedMemberCouponList(Long memberId);

    MemberCouponUpdateResDto updateCoupon(Long memberCouponId);
    IssuedMemberCouponResDto getMemberCoupon(Long memberCouponId);
}
