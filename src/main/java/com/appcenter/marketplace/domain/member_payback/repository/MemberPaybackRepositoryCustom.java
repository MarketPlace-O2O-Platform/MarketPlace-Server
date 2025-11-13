package com.appcenter.marketplace.domain.member_payback.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;

import java.util.List;
import java.util.Optional;

public interface MemberPaybackRepositoryCustom {
    boolean existCouponByMemberId(Long memberId, Long paybackId);
    Optional<MemberPayback> findByCouponIdAndMemberId(Long memberId, Long paybackId);
    List<IssuedCouponRes> findIssuedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    List<IssuedCouponRes> findEndedCouponResByMemberId(Long memberId, Long memberCouponId, Integer size);
    void check3DaysCoupons();
    ReceiptRes findReceiptByMemberId(Long memberPaybackId, Long couponId);
    List<MemberPayback> findMemberPaybacksByMarketId(Long marketId);
    List<AdminReceiptRes> findReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size);
}
