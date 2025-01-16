package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponRes> findCouponsForOwnerByMarketId(Long marketId, Long couponId, Integer size);

    List<CouponMemberRes> findCouponsForMemberByMarketId(Long marketId, Long couponId, Integer size);
}
