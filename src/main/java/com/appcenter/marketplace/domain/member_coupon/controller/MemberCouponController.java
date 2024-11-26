package com.appcenter.marketplace.domain.member_coupon.controller;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[회원 쿠폰]", description = "[회원] 회원의 쿠폰 발급 및 사용처리, 리스트 확인")
@RestController
@RequestMapping("/api/members/coupons")
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @Operation(summary = "회원 쿠폰 발급", description = "회원은 해당 marketId로 유효한 쿠폰을 발급받을 수 있습니다. " +
                                             "<br> '유효한 쿠폰'은 공개처리가 된 쿠폰, 만료되지 않은 쿠폰을 뜻합니다." )
    @PostMapping("/{couponId}")
    public ResponseEntity<CommonResponse<Object>> issuedCoupon(@RequestParam(name="memberId") Long memberId,
                                               @PathVariable(name="couponId") Long couponId) {
        memberCouponService.issuedCoupon(memberId, couponId);
        return ResponseEntity.status(COUPON_ISSUED.getStatus()).body(CommonResponse.from(COUPON_ISSUED.getMessage()));
    }

    @Operation(summary = "사용 가능한 쿠폰 리스트", description = "쿠폰 리스트를 조회하면 기본적으로 조회되는 리스트입니다." +
                                                    "<br> '사용가능'한 쿠폰 리스트를 의미합니다." )
    @GetMapping("/valid")
    public ResponseEntity<CommonResponse<List<IssuedCouponRes>>> getCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getMemberCouponList(memberId)));
    }

    @Operation(summary = "만료된 쿠폰 리스트", description = "기간이 만료된 쿠폰 리스트를 조회합니다." +
                                                 "<br> 조건은 다음과 같습니다." +
                                                 "1. 쿠폰의 deadLine(만료날짜) 이후여야 합니다." +
                                                 "2. 쿠폰은 발급받았지만, 사용처리가 되지 않아야 합니다.")
    @GetMapping("/expired")
    public ResponseEntity<CommonResponse<List<IssuedCouponRes>>> getExpiredCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getExpiredMemberCouponList(memberId)));
    }

    @Operation(summary = "사용완료된 쿠폰 리스트", description = "사용이 완료된 쿠폰 리스트를 조회합니다." +
                                                "<br> 조건은 다음과 같습니다." +
                                                "1. 쿠폰을 발급받고, 사용처리가 완료되어야 합니다." +
                                                "2. 쿠폰의 deadLine(만료날짜)와는 상관이 없습니다.")
    @GetMapping("/used")
    public ResponseEntity<CommonResponse<List<IssuedCouponRes>>> getUsedCouponList(@RequestParam(name="memberId")Long memberId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getUsedMemberCouponList(memberId)));
    }

    @Operation(summary = "회원 쿠폰 사용처리", description = "회원은 발급받은 memberCouponId로 사용처리를 할 수 있습니다." )
    @PutMapping
    public ResponseEntity<CommonResponse<CouponHandleRes>> updateCoupon(@RequestParam(name="memberCouponId") Long couponId){
        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage(),memberCouponService.updateCoupon(couponId)));
    }

    @Operation(summary = "회원의 발급 쿠폰 단일 조회", description = "회원은 발급받은 memberCouponId로 1개의 쿠폰 정보를 확인할 수 있습니다." )
    @GetMapping("/{memberCouponId}")
    public ResponseEntity<CommonResponse<IssuedCouponRes>> getMemberCoupon(@RequestParam(name="memberCouponId")Long couponId){
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getMemberCoupon(couponId)));
    }
}
