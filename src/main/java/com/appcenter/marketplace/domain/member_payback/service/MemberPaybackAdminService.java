package com.appcenter.marketplace.domain.member_payback.service;

import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;

public interface MemberPaybackAdminService {
    
    CouponHandleRes manageCoupon(Long paybackId);
}
