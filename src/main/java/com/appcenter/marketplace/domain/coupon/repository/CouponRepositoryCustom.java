package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;

import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponResDto> findOwnerCouponResDtoByMarketId(Long marketId);
    List<CouponMemberResDto> findMemberCouponResDtoByMarketId(Long marketId);
}
