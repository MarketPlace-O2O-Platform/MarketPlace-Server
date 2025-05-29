package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService {

   CouponPageRes<CouponRes> getCouponList(Long memberId, Long marketId, Long couponId, Integer size);

   CouponPageRes<LatestCouponRes> getLatestCouponPage(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

   CouponPageRes<PopularCouponRes> getPopularCouponPage(Long memberId, Long count, Long couponId, Integer size);

   List<ClosingCouponRes> getTopClosingCoupon(Integer size);

   List<LatestCouponRes> getTopLatestCoupon(Integer size);
}
