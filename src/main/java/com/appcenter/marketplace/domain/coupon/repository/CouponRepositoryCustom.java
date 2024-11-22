package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponLatestTopResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMarketResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {
    List<CouponResDto> findOwnerCouponResDtoByMarketId(Long marketId);
    List<CouponMemberResDto> findMemberCouponResDtoByMarketId(Long marketId);
    List<CouponLatestTopResDto> findLatestTopCouponDtoListByMarket(Integer size);
    List<CouponMarketResDto> findLatestCouponMarketResDtoListByMarket(LocalDateTime modifiedAt, Long couponId, Integer size);
}
