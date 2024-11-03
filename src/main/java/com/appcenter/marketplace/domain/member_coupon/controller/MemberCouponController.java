package com.appcenter.marketplace.domain.member_coupon.controller;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedMemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponListResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponResDto;
import com.appcenter.marketplace.domain.member_coupon.dto.res.MemberCouponUpdateResDto;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequestMapping("/api/members/coupons")
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponService memberCouponService;


    @PostMapping("/{couponId}")
    public ResponseEntity<MemberCouponResDto> issuedCoupon(@RequestParam(name="memberId") Long memberId,
                                                           @PathVariable(name="couponId") Long couponId){
        return ResponseEntity.status(COUPON_ISSUED.getStatus()).body(memberCouponService.issuedCoupon(memberId, couponId));
    }

    @GetMapping("/valid")
    public ResponseEntity<CommonResponse<MemberCouponListResDto>> getCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getMemberCouponList(memberId)));
    }

    @GetMapping("/expired")
    public ResponseEntity<CommonResponse<MemberCouponListResDto>> getExpiredCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getExpiredMemberCouponList(memberId)));
    }

    @GetMapping("/used")
    public ResponseEntity<CommonResponse<MemberCouponListResDto>> getUsedCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getUsedMemberCouponList(memberId)));
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MemberCouponUpdateResDto>> updateCoupon(@RequestParam(name="memberCouponId") Long couponId){
        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage(),memberCouponService.updateCoupon(couponId)));
    }

    @GetMapping("/{memberCouponId}")
    public ResponseEntity<CommonResponse<IssuedMemberCouponResDto>> getMemberCoupon(@RequestParam(name="memberCouponId")Long couponId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getMemberCoupon(couponId)));
    }
}
