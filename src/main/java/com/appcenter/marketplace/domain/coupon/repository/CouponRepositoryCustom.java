package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponResDto> findOwnerCouponResDtoByMarketId(Long marketId);
    List<CouponMemberResDto> findMemberCouponResDtoByMarketId(Long marketId);
}
