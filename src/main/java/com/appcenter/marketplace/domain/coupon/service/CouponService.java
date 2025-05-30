package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService {

   CouponPageRes<CouponRes> getCouponList(Long memberId, Long marketId, Long couponId, Integer size);

   CouponPageRes<CouponRes> getLatestCouponPage(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

   CouponPageRes<CouponRes> getPopularCouponPage(Long memberId, Long count, Long couponId, Integer size);

   List<TopClosingCouponRes> getTopClosingCoupon(Integer size);

   List<TopLatestCouponRes> getTopLatestCoupon(Integer size);

   List<TopPopularCouponRes> getTopPopularCoupon(Integer size);
}
