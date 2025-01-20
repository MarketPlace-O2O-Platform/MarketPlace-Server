package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.ClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;

public interface CouponService {

   CouponPageRes<CouponMemberRes> getCouponList(Long marketId, Long couponId, Integer size);

   CouponPageRes<ClosingCouponRes> getClosingCouponPage(Integer size);
}
