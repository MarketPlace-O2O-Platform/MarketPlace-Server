package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.dto.res.LatestCouponRes;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponRes> findCouponsForOwnerByMarketId(Long marketId, Long couponId, Integer size);

    List<CouponRes> findCouponsForMemberByMarketId(Long memberId, Long marketId, Long couponId, Integer size);

    List<LatestCouponRes> findLatestCouponList(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

    List<PopularCouponRes> findPopularCouponList(Long memberId, Long count, Long couponId, Integer size);

    List<ClosingCouponRes> findTopClosingCouponList(Integer size);

    List<LatestCouponRes> findTopLatestCouponList(Integer size);
}
