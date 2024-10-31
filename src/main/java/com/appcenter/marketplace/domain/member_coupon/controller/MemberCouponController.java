package com.appcenter.marketplace.domain.member_coupon.controller;

import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponService memberCouponService;


    @PostMapping("/{couponId}")
    public ResponseEntity<MemberCouponResDto> issuedCoupon(@RequestParam(name="memberId") Long memberId,
                                                           @PathVariable(name="couponId") Long couponId){
        return ResponseEntity.status(COUPON_ISSUED.getStatus()).body(memberCouponService.issuedCoupon(memberId, couponId));
    }

}
