package com.appcenter.marketplace.domain.member_coupon.service;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponListResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponUpdateResDto;

public interface MemberCouponService {
    MemberCouponResDto issuedCoupon(Long memberId, Long couponId);
    MemberCouponListResDto getMemberCouponList(Long memberId);
    MemberCouponListResDto getExpiredMemberCouponList(Long memberId);
    MemberCouponListResDto getUsedMemberCouponList(Long memberId);
    MemberCouponUpdateResDto updateCoupon(Long memberCouponId);
    IssuedMemberCouponResDto getMemberCoupon(Long memberCouponId);
}
