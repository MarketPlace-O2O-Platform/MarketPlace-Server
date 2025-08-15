package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;

import java.util.List;

public interface PaybackRepositoryCustom {
    List<PaybackRes> findCouponsForAdminByMarketId(Long marketId, Long couponId, Integer size);

    List<PaybackRes> findCouponsForMembersByMarketId(Long marketId, Long memberId, Long couponId, Integer size);
}
