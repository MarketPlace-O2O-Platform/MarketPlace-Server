package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopClosingCouponRes;

import java.util.List;

public interface CouponService {

   CouponPageRes<CouponMemberRes> getCouponList(Long marketId, Long couponId, Integer size);

   List<TopClosingCouponRes> getTopClosingCoupons(Integer size);
}
