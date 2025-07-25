package com.appcenter.marketplace.domain.payback.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.repository.PaybackRepository;
import com.appcenter.marketplace.domain.payback.service.PaybackService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaybackServiceImpl implements PaybackService {

    private final PaybackRepository paybackRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public PaybackRes createCoupon(PaybackReq couponReq, Long marketId) {
        Market market = findMarketById(marketId);
        Payback payback = paybackRepository.save(couponReq.ofCreate(market));

        // 나중에 관리자 -> 알림 추가 ( 회의 후 결정)

        return PaybackRes.toDto(payback);
    }

    @Override
    @Transactional
    public PaybackRes updateCoupon(PaybackReq couponReq, Long couponId) {
        Payback payback = findPaybackById(couponId);
        payback.update(couponReq);
        return PaybackRes.toDto(payback);
    }

    @Override
    @Transactional
    public void updateCouponHidden(Long couponId) {
        Payback payback = findPaybackById(couponId);
        payback.updateHidden();
    }

    // [관리자용] 전체 쿠폰 리스트 조회
    @Override
    public CouponPageRes<PaybackRes> getCouponListForAdmin(Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<PaybackRes> paybackResList = paybackRepository.findCouponsForAdminByMarketId(market.getId(), couponId, size);
        return checkNextPageAndReturn(paybackResList, size);
    }

    @Override
    public CouponPageRes<PaybackRes> getCouponListForMembers(Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<PaybackRes> paybackResList = paybackRepository.findCouponsForMembersByMarketId(market.getId(), couponId, size);
        return checkNextPageAndReturn(paybackResList, size);
    }

    @Override
    @Transactional
    public void softDeleteCoupon(Long couponId) {
        Payback payback = findPaybackById(couponId);
        payback.softDeleteCoupon();
    }

    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
    }

    private Payback findPaybackById(Long couponId) {
        Payback payback = paybackRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if (!payback.getIsDeleted()){
            return payback;
        }
        else throw new CustomException(COUPON_IS_DELETED);
    }

    private <T> CouponPageRes<T> checkNextPageAndReturn(List<T> couponList, Integer size) {
        boolean hasNext = false;

        if(couponList.size() > size){
            hasNext = true;
            couponList.remove(size.intValue());
        }

        return new CouponPageRes<>(couponList, hasNext);
    }
}
