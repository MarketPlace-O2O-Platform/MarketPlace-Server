package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;

import java.util.List;

public interface CouponService {
   List<CouponMemberResDto> getCouponList(Long marketId);
}
