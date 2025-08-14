package com.appcenter.marketplace.domain.member_payback.service;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;
import org.springframework.web.multipart.MultipartFile;

public interface MemberPaybackService {

    // 환급 쿠폰 발급
    void issuedCoupon(Long memberId, Long couponId);

    // 발급 받은 환급 쿠폰 목록 조회 (유효)
    CouponPageRes<IssuedCouponRes> getPaybackCouponList(Long memberId, MemberCouponType type, Long couponId, Integer size);

    // 쿠폰 영수증 제출 ( 쿠폰 사용 ) (병합 후 진행)
    CouponHandleRes updateCoupon(Long memberId, Long memberPaybackId, MultipartFile receiptImages);


    // 3일뒤 만료 처리
    void check3DaysCoupons();

}

