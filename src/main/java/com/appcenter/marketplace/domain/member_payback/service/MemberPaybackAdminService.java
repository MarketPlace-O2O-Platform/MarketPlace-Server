package com.appcenter.marketplace.domain.member_payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.CouponPaybackStatsRes;

public interface MemberPaybackAdminService {

    CouponHandleRes manageCoupon(Long memberPaybackId);

    CouponPageRes<AdminReceiptRes> getReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size);

    AdminReceiptRes getReceiptDetail(Long memberPaybackId);

    CouponPaybackStatsRes getCouponPaybackStats();

}
