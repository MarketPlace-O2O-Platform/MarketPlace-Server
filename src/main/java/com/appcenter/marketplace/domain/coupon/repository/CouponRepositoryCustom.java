package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponMemberResDto> findAllByMarketId(Long marketId);
    List<CouponMemberResDto> findCouponsByMarketId(Long marketId);
}
