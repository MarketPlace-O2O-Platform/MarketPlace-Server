package com.appcenter.marketplace.domain.beta.repository;

import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;

import java.util.List;

public interface BetaCouponCustom {
    List<BetaCouponRes> findAllCoupons(Long memberId, Long betaCouponId, Integer size);
    List<BetaCouponRes> findAllCouponsByCategory(Long memberId, Long betaCouponId, String major, Integer size);
}
