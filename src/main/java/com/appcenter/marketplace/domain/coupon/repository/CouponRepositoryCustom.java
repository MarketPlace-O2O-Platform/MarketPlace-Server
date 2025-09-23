package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponRes> findCouponsForOwnerByMarketId(Long marketId, Long couponId, Integer size);

    List<CouponRes> findCouponsForMemberByMarketId(Long memberId, Long marketId, Long couponId, Integer size);

    List<CouponRes> findLatestCouponList(Long memberId, LocalDateTime createdAt, Long couponId, Integer size);

    List<CouponRes> findPopularCouponList(Long memberId, Long count, Long couponId, Integer size);

    List<TopClosingCouponRes> findTopClosingCouponList(Integer size);

    List<TopLatestCouponRes> findTopLatestCouponList(Integer size);

    List<TopPopularCouponRes> findTopPopularCouponList(Integer size);

    List<CouponRes> findCouponsForAdmin(Long couponId, Long marketId, Integer size);

}
