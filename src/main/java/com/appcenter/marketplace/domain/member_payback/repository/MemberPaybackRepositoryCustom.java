package com.appcenter.marketplace.domain.member_payback.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;

import com.appcenter.marketplace.domain.member_payback.dto.res.MemberReceiptHistoryRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptItemRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMarketPaybackRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMemberReceiptRes;

import java.time.LocalDateTime;
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
    AdminReceiptRes findReceiptDetailForAdmin(Long memberPaybackId);
    long countByMemberCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<ReceiptItemRes> findReceiptsByMemberId(Long memberId);
    List<TopMarketPaybackRes> findTopMarketsByPaybackCount(LocalDateTime start, LocalDateTime end);
    List<TopMarketPaybackRes> findTopMarketsByCompletedPaybackCount(LocalDateTime start, LocalDateTime end);
    List<TopMemberReceiptRes> findTopMembersByReceiptCount(LocalDateTime start, LocalDateTime end);
    List<TopMemberReceiptRes> findMemberReceiptCountByCalendar(LocalDateTime start, LocalDateTime end);
}
