package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.payback.repository.PaybackRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final PaybackRepository paybackRepository;
    private final MarketRepository marketRepository;

    @Override
    public CouponPageRes<CouponRes> getCouponList(Long memberId, Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<CouponRes> couponList = couponRepository.findCouponsForMemberByMarketId(memberId, market.getId(), couponId, size);
        return checkNextPageAndReturn(couponList, size);
    }

    // 최신 등록 쿠폰 더보기 조회 (Coupon + Payback 병합, createdAt 순)
    @Override
    public CouponPageRes<CouponRes> getLatestCouponPage(Long memberId, LocalDateTime lastCreatedAt, Long couponId, CouponType couponType, Integer size) {
        // Coupon과 Payback을 각각 조회 (size + 1개씩)
        List<CouponRes> coupons = couponRepository.findLatestCouponList(memberId, lastCreatedAt, couponId, size + 1);
        List<CouponRes> paybacks = paybackRepository.findLatestPaybackList(memberId, lastCreatedAt, couponId, size + 1);

        // createdAt 기준으로 병합 정렬
        List<CouponRes> mergedList = Stream.concat(coupons.stream(), paybacks.stream())
                .sorted(Comparator.comparing(CouponRes::getCouponCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(CouponRes::getCouponId, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // 커서 필터링 (lastCreatedAt, couponId, couponType 기준)
        List<CouponRes> filteredList = mergedList.stream()
                .filter(c -> {
                    if (lastCreatedAt == null || couponId == null || couponType == null) {
                        return true;
                    }
                    // 이전 커서 이후의 데이터만 포함
                    if (c.getCouponCreatedAt().isBefore(lastCreatedAt)) {
                        return true;
                    } else if (c.getCouponCreatedAt().equals(lastCreatedAt)) {
                        // 같은 createdAt인 경우, couponType과 id로 판단
                        if (c.getCouponType() == couponType) {
                            return c.getCouponId() < couponId;
                        }
                        return c.getCouponId() <= couponId;
                    }
                    return false;
                })
                .limit(size + 1)
                .collect(Collectors.toList());

        return checkNextPageAndReturn(filteredList, size);
    }

    // 인기 쿠폰 더보기 조회 (Coupon 발급 횟수 순 → Payback 매장 orderNo 순)
    @Override
    public CouponPageRes<CouponRes> getPopularCouponPage(Long memberId, Long count, Long couponId, CouponType couponType, Integer size) {
        List<CouponRes> result = new ArrayList<>();

        if (couponType == null || couponType == CouponType.GIFT) {
            // Coupon 조회
            List<CouponRes> coupons = couponRepository.findPopularCouponList(memberId, count, couponId, size + 1);
            result.addAll(coupons);

            // Coupon이 size보다 적으면 Payback 추가
            if (result.size() <= size) {
                int remainingSize = size + 1 - result.size();
                List<CouponRes> paybacks = paybackRepository.findPopularPaybackList(memberId, null, null, remainingSize);
                result.addAll(paybacks);
            }
        } else {
            // PAYBACK 타입이면 Payback만 조회
            // count 값을 orderNo로 해석
            Integer orderNo = count != null ? count.intValue() : null;
            List<CouponRes> paybacks = paybackRepository.findPopularPaybackList(memberId, orderNo, couponId, size + 1);
            result.addAll(paybacks);
        }

        return checkNextPageAndReturn(result, size);
    }

    // 마감 임박 쿠폰 TOP 조회 (일반 쿠폰 + Payback 쿠폰)
    @Override
    @Cacheable(value = "CLOSING_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopClosingCouponRes> getTopClosingCoupon(Integer size) {
        List<TopClosingCouponRes> coupons = couponRepository.findTopClosingCouponList(size);

        // 일반 쿠폰이 size보다 적으면 Payback으로 대체
        if (coupons.size() < size) {
            int remainingSize = size - coupons.size();
            List<TopClosingCouponRes> paybacks = paybackRepository.findTopClosingPaybackList(remainingSize);

            List<TopClosingCouponRes> result = new ArrayList<>(coupons);
            result.addAll(paybacks);
            return result;
        }

        return coupons;
    }

    // 최신 등록 쿠폰 TOP 조회 (일반 쿠폰 + Payback 쿠폰)
    @Override
    @Cacheable(value = "LATEST_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopLatestCouponRes> getTopLatestCoupon(Integer size) {
        List<TopLatestCouponRes> coupons = couponRepository.findTopLatestCouponList(size);

        // 일반 쿠폰이 size보다 적으면 Payback으로 대체
        if (coupons.size() < size) {
            int remainingSize = size - coupons.size();
            List<TopLatestCouponRes> paybacks = paybackRepository.findTopLatestPaybackList(remainingSize);

            List<TopLatestCouponRes> result = new ArrayList<>(coupons);
            result.addAll(paybacks);
            return result;
        }

        return coupons;
    }

    // 인기 쿠폰 TOP 조회 (일반 쿠폰 + Payback 쿠폰)
    @Override
    @Cacheable(value = "POPULAR_COUPONS", key = "#size", unless = "#result.isEmpty()")
    public List<TopPopularCouponRes> getTopPopularCoupon(Integer size) {
        List<TopPopularCouponRes> coupons = couponRepository.findTopPopularCouponList(size);

        // 일반 쿠폰이 size보다 적으면 Payback으로 대체
        if (coupons.size() < size) {
            int remainingSize = size - coupons.size();
            List<TopPopularCouponRes> paybacks = paybackRepository.findTopPopularPaybackList(remainingSize);

            List<TopPopularCouponRes> result = new ArrayList<>(coupons);
            result.addAll(paybacks);
            return result;
        }

        return coupons;
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
