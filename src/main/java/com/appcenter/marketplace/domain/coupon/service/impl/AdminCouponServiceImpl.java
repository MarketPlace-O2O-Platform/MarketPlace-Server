package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.AdminCouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.repository.PaybackRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.fcm.event.SendNewCouponFcmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCouponServiceImpl implements AdminCouponService {
    private final ApplicationEventPublisher eventPublisher;
    private final CouponRepository couponRepository;
    private final PaybackRepository paybackRepository;
    private final MarketRepository marketRepository;

    // ===== 일반 쿠폰 관리 =====
    @Override
    public CouponPageRes<CouponRes> getAllCoupons(Long couponId, Long marketId, Integer size) {
        List<CouponRes> couponList = couponRepository.findCouponsForAdmin(couponId, marketId, size);
        return checkNextPageAndReturn(couponList, size);
    }

    @Override
    public CouponRes getCoupon(Long couponId) {
        return CouponRes.toDto(findCouponById(couponId));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"CLOSING_COUPONS", "LATEST_COUPONS", "POPULAR_COUPONS"}, allEntries = true)
    public CouponRes createCoupon(CouponReq couponReq, Long marketId) {
        Market market = findMarketById(marketId);
        Coupon coupon = couponRepository.save(couponReq.ofCreate(market));

        eventPublisher.publishEvent(new SendNewCouponFcmEvent(market, coupon));

        return CouponRes.toDto(coupon);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"CLOSING_COUPONS", "LATEST_COUPONS", "POPULAR_COUPONS"}, allEntries = true)
    public CouponRes updateCoupon(CouponReq couponReq, Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.update(couponReq);
        return CouponRes.toDto(coupon);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"CLOSING_COUPONS", "LATEST_COUPONS", "POPULAR_COUPONS"}, allEntries = true)
    public void updateCouponHidden(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.updateHidden();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"CLOSING_COUPONS", "LATEST_COUPONS", "POPULAR_COUPONS"}, allEntries = true)
    public void softDeleteCoupon(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.softDeleteCoupon();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"CLOSING_COUPONS", "LATEST_COUPONS", "POPULAR_COUPONS"}, allEntries = true)
    public void softDeleteCoupons(List<Long> couponIds) {
        List<Coupon> coupons = couponRepository.findAllById(couponIds);
        coupons.forEach(Coupon::softDeleteCoupon);
    }

    // ===== Private Helper Methods =====
    private Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
    }

    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
    }

    private <T> CouponPageRes<T> checkNextPageAndReturn(List<T> couponList, Integer size) {
        boolean hasNext = couponList.size() > size;

        if (hasNext) {
            couponList = couponList.subList(0, size);
        }

        return new CouponPageRes<>(couponList, hasNext);
    }
}