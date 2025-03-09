package com.appcenter.marketplace.domain.beta.service;

import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponPageRes;
import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;

public interface BetaCouponService {
    BetaCouponPageRes<BetaCouponRes> getBetaCouponList(Long memberId, Long betaCouponId, String major, Integer size);
    void useBetaCoupon(Long betaCouponId);
}
