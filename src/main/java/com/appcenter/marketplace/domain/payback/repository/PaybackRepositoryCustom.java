package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopLatestCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.TopPopularCouponRes;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;

import java.time.LocalDateTime;
import java.util.List;

public interface PaybackRepositoryCustom {
    List<PaybackRes> findCouponsForAdminByMarketId(Long marketId, Long couponId, Integer size);

    List<PaybackRes> findCouponsForMembersByMarketId(Long marketId, Long memberId, Long couponId, Integer size);

    List<PaybackRes> findPaybackCouponsForAdmin(Long couponId, Integer size);

    List<Payback> findPaybacksByMarketId(Long marketId);

    List<TopClosingCouponRes> findTopClosingPaybackList(Integer size);

    List<TopLatestCouponRes> findTopLatestPaybackList(Integer size);

    List<TopPopularCouponRes> findTopPopularPaybackList(Integer size);

    // 최신 등록 Payback 페이징 조회
    List<CouponRes> findLatestPaybackList(Long memberId, LocalDateTime createdAt, Long paybackId, Integer size);

    // 인기 Payback 페이징 조회 (매장 orderNo 순)
    List<CouponRes> findPopularPaybackList(Long memberId, Integer orderNo, Long paybackId, Integer size);
}
