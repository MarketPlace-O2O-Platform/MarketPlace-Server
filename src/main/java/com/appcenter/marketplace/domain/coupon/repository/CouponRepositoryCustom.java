package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.util.List;

public interface CouponRepositoryCustom {

    List<CouponRes> findOwnerCouponResDtoByMarketId(Long marketId);

    List<CouponMemberRes> findMemberCouponResDtoByMarketId(Long marketId);
}
