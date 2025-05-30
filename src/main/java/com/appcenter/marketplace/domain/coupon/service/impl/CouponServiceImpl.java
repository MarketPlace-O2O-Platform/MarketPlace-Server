package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;

    @Override
    public CouponPageRes<CouponRes> getCouponList(Long memberId, Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<CouponRes> couponList = couponRepository.findCouponsForMemberByMarketId(memberId, market.getId(), couponId, size);
        return checkNextPageAndReturn(couponList, size);
    }

    // 최신 등록 쿠폰 더보기 조회
    @Override
    public CouponPageRes<CouponRes> getLatestCouponPage(Long memberId, LocalDateTime lastCreatedAt, Long couponId, Integer size) {
        List<CouponRes> resDtoList = couponRepository.findLatestCouponList(memberId, lastCreatedAt, couponId, size);

        return checkNextPageAndReturn(resDtoList, size);
    }

    // 인기 쿠폰 더보기 조회
    @Override
    public CouponPageRes<CouponRes> getPopularCouponPage(Long memberId, Long count, Long couponId, Integer size) {
        List<CouponRes> resDtoList = couponRepository.findPopularCouponList(memberId, count, couponId, size);
        return checkNextPageAndReturn(resDtoList, size);
    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    @Cacheable(value = "CLOSING_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopClosingCouponRes> getTopClosingCoupon(Integer size) {
        return couponRepository.findTopClosingCouponList(size);
    }

    // 최신 등록 쿠폰 TOP 조회
    @Override
    @Cacheable(value = "LATEST_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopLatestCouponRes> getTopLatestCoupon(Integer size) {
        return couponRepository.findTopLatestCouponList(size);
    }

    // 인기 쿠폰 TOP 조회
    @Override
    @Cacheable(value = "POPULAR_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopPopularCouponRes> getTopPopularCoupon(Integer size) {
        return couponRepository.findTopPopularCouponList(size);
    }

    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));

    }

    private <T>CouponPageRes<T> checkNextPageAndReturn(List<T> couponList, Integer size) {
        boolean hasNext = false;

        if(couponList.size() > size){
            hasNext = true;
            couponList.remove(size.intValue());
        }

        return new CouponPageRes<>(couponList, hasNext);
    }
}
