package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.ClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.LatestCouponRes;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService {

   CouponPageRes<CouponMemberRes> getCouponList(Long marketId, Long couponId, Integer size);

   CouponPageRes<LatestCouponRes> getLatestCouponPage(LocalDateTime createdAt, Long couponId, Integer size);

   List<ClosingCouponRes> getClosingCouponPage(Integer size);
}
