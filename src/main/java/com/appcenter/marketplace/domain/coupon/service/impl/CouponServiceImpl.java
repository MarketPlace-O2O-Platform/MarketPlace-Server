package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.coupon.dto.res.LatestCouponRes;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
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
    public CouponPageRes<CouponMemberRes> getCouponList(Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<CouponMemberRes> couponList = couponRepository.findCouponsForMemberByMarketId(market.getId(), couponId, size);
        return checkNextPageAndReturn(couponList, size);
    }

    // 최신 등록 쿠폰의 매장 더보기 조회
    @Override
    public CouponPageRes<LatestCouponRes> getLatestCouponPage(LocalDateTime lastCreatedAt, Long lastCouponId, Integer size) {
        List<LatestCouponRes> resDtoList = couponRepository.findLatestCouponList(lastCreatedAt, lastCouponId, size);

        if (resDtoList.isEmpty())
            throw new CustomException(MARKET_NOT_EXIST);

        return checkNextPageAndReturn(resDtoList, size);
    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    public List<ClosingCouponRes> getClosingCouponPage(Integer size) {
        return couponRepository.findClosingCouponList(size);
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
