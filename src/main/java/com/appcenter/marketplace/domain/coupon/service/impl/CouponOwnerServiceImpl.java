package com.appcenter.marketplace.domain.coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.CouponOwnerService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.config.AsyncConfig;
import com.appcenter.marketplace.global.exception.CustomException;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponOwnerServiceImpl implements CouponOwnerService {

    private final CouponRepository couponRepository;
    private final MarketRepository marketRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final AsyncConfig asyncConfig;

    @Override
    @Transactional
    public CouponRes createCoupon(CouponReq couponReq, Long marketId) {
        Market market = findMarketById(marketId);
        Coupon coupon = couponRepository.save(couponReq.ofCreate(market));

        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(coupon.getName())
                                .setBody(coupon.getDescription())
                                .build()
                )
                .setTopic("market-"+ market.getId())
                .build();


        ApiFuture<String> apiFuture= firebaseMessaging.sendAsync(message);

        apiFuture.addListener(() ->{
            try{
                String response = apiFuture.get();
                log.info("토픽 구독 성공: {}", response);
            } catch (ExecutionException | InterruptedException e) {
                log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
                throw new CustomException(StatusCode.FCM_SEND_FAIL);
            }
        }, asyncConfig.getFcmExecutor());

        return CouponRes.toDto(coupon);
    }

    @Override
    public CouponRes getCoupon(Long couponId) {
        return CouponRes.toDto(findCouponById(couponId));
    }

    @Override
    public CouponPageRes<CouponRes> getCouponList(Long marketId, Long couponId, Integer size) {
        Market market = findMarketById(marketId);
        List<CouponRes> couponResList = couponRepository.findCouponsForOwnerByMarketId(market.getId(), couponId, size);

        return checkNextPageAndReturn(couponResList, size);
    }

    @Override
    @Transactional
    public CouponRes updateCoupon(CouponReq couponReq, Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.update(couponReq);
        return CouponRes.toDto(coupon);
    }

    @Override
    @Transactional
    public void updateCouponHidden(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        coupon.updateHidden();
    }

    @Override
    @Transactional
    public void softDeleteCoupon(Long couponId) {
        Coupon coupon = findCouponById(couponId);
        // 소프트 딜리트 적용
        coupon.softDeleteCoupon();
    }

    @Override
    @Transactional
    public void hardDeleteCoupon(Long marketId) {
       couponRepository.deleteAllByMarketId(marketId);
    }


    @Override
    public List<Coupon> getCoupons(Long marketId) {
        return couponRepository.findByMarketId(marketId);
    }


    private Market findMarketById(Long marketId) {
        return marketRepository.findById(marketId).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));

    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        // 나중에 쿼리로도 적용 예정
        if (!coupon.getIsDeleted())
            return coupon;

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
