package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponListResDto;

public interface CouponService {
   CouponListResDto getCouponList(Long marketId);
}
