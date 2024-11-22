package com.appcenter.marketplace.domain.coupon.service;


import com.appcenter.marketplace.domain.coupon.dto.res.CouponClosingTopResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponLatestTopResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMarketPageResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponService {
   List<CouponMemberResDto> getCouponList(Long marketId);
   List<CouponLatestTopResDto> getCouponLatestTop(Integer size);
   CouponMarketPageResDto getLatestCouponList(LocalDateTime modifiedAt, Long couponId, Integer size);
   List<CouponClosingTopResDto> getCouponClosingTop(Integer size);

}
