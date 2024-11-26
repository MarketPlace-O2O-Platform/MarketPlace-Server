package com.appcenter.marketplace.domain.coupon.service;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberRes;

import java.util.List;

public interface CouponService {

   List<CouponMemberRes> getCouponList(Long marketId);

}
