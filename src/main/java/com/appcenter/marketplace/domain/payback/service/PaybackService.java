package com.appcenter.marketplace.domain.payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;

import java.util.List;


// 관리자용
public interface PaybackService {

    // 환급 쿠폰 등록 메서드
    PaybackRes createCoupon(PaybackReq req, Long marketId);

    // 환급 쿠폰 수정 메서드
    PaybackRes updateCoupon(PaybackReq req, Long couponId);

    // 환급 쿠폰 숨김 처리 메서드
    void updateCouponHidden(Long couponId);

    // 전체 환급 쿠폰리스트 조회
    CouponPageRes<PaybackRes> getAllPaybackCoupons(Long couponId, Integer size);

    // 환급쿠폰 상세 조회
    PaybackRes getPaybackCoupon(Long couponId);

    // 매장별 환급 쿠폰 조회 메서드 (관리자용)
    CouponPageRes<PaybackRes> getCouponListForAdmin(Long marketId, Long couponId, Integer size);

    // 매장별 환급 쿠폰 조회 메서드 (유저용 - isHidden False)
    CouponPageRes<PaybackRes> getCouponListForMembers(Long marketId, Long memberId, Long couponId, Integer size);

    // 환급 쿠폰 삭제 메서드
    void softDeleteCoupon(Long couponId);

    // 환급 쿠폰 삭제 메서드 (소프트딜리트)
    void softDeletePaybackCoupons(List<Long> couponIds);

    // 매장별 환급 쿠폰 일괄 삭제 메서드 (매장 삭제용)
    void hardDeleteCouponsByMarket(Long marketId);

}
