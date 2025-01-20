package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.*;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public CouponPageRes<CouponMemberRes> getCouponList(Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<CouponMemberRes> couponList = couponRepository.findCouponsForMemberByMarketId(market.getId(), couponId, size);
        return checkNextPageAndReturn(couponList, size);
    }

    // 마감 임박 쿠폰 TOP 조회
    @Override
    public List<TopClosingCouponRes> getTopClosingCoupons(Integer size) {
        return marketRepository.findTopClosingCoupons(size);
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
