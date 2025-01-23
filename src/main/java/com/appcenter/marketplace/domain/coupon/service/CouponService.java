package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.ClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.LatestCouponRes;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService {

   CouponPageRes<CouponRes> getCouponList(Long memberId, Long marketId, Long couponId, Integer size);

   CouponPageRes<LatestCouponRes> getLatestCouponPage(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

   List<ClosingCouponRes> getClosingCouponPage(Integer size);
}
