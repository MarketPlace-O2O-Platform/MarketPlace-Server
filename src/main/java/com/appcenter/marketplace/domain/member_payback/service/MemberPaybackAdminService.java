package com.appcenter.marketplace.domain.member_payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;

public interface MemberPaybackAdminService {

    CouponHandleRes manageCoupon(Long memberPaybackId);

    CouponPageRes<AdminReceiptRes> getReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size);

}
