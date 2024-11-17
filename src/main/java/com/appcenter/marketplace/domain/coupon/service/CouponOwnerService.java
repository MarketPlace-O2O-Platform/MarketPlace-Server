package com.appcenter.marketplace.domain.coupon.service;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;

import java.util.List;

public interface CouponOwnerService {

    // 쿠폰 등록 메서드
    CouponResDto createCoupon(CouponReqDto couponReqDto, Long marketId);

    // 쿠폰 확인 메서드
    CouponResDto getCoupon(Long couponId);

    // 매장별 전체 쿠폰 확인 메서드
    List<CouponResDto> getCouponList(Long marketId);

    // 쿠폰 내용 수정 메서드
    CouponResDto updateCoupon(CouponUpdateReqDto couponUpdateReqDto, Long couponId);

    // 쿠폰 숨김 처리 메서드
    CouponHiddenResDto updateCouponHidden(Long couponId);

    // 쿠폰 삭제 메서드
    void deleteCoupon(Long couponId);
}
