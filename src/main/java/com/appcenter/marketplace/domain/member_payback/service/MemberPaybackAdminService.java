package com.appcenter.marketplace.domain.member_payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.CouponPaybackStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.RecentMemberPaybackStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.MemberReceiptHistoryRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMarketPaybackRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptSubmissionStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMemberReceiptRes;

import java.time.LocalDate;
import java.util.List;

public interface MemberPaybackAdminService {

    CouponHandleRes manageCoupon(Long memberPaybackId);

    CouponPageRes<AdminReceiptRes> getReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size);

    AdminReceiptRes getReceiptDetail(Long memberPaybackId);

    CouponPaybackStatsRes getCouponPaybackStats();

    RecentMemberPaybackStatsRes getRecentMemberPaybackStats();

    List<TopMarketPaybackRes> getTopMarketsByPaybackCount(String period);

    List<TopMarketPaybackRes> getTopMarketsByCompletedPaybackCount(String period);

    List<TopMemberReceiptRes> getTopMembersByReceiptCount(String period);

    List<TopMemberReceiptRes> getMemberReceiptCountByCalendar(String period);

    MemberReceiptHistoryRes getReceiptHistoryByMemberId(Long memberId);

    ReceiptSubmissionStatsRes getReceiptSubmissionStats(String period, LocalDate startDate, LocalDate endDate);

}
