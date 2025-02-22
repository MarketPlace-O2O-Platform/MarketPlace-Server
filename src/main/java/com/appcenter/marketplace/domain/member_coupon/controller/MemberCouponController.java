package com.appcenter.marketplace.domain.member_coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.domain.member_coupon.MemberCouponType.*;
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
    public ResponseEntity<CommonResponse<Object>> issuedCoupon(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable(name="couponId") Long couponId) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberCouponService.issuedCoupon(memberId, couponId);
        return ResponseEntity.status(COUPON_ISSUED.getStatus()).body(CommonResponse.from(COUPON_ISSUED.getMessage()));
    }

    @Operation(summary = "회원의 쿠폰 리스트", description = "type은 'ISSUED(사용가능한)', 'EXPIRED(기간 만료된)', 'USED(사용 완료된)' 중에서 작성해주시면 됩니다. <br>" +
            "쿠폰 리스트를 조회하면 기본적으로 조회되는 리스트는 ISSUED로 사용가능한 쿠폰 리스트입니다.<br> " +
            "무한 스크롤 방식으로 hasNext가 true 면 마지막 데이터의 memberCouponId를 입력하여, 다음 페이지==다음 데이터를 받아올 수 있습니다." )
    @GetMapping
    public ResponseEntity<CommonResponse<CouponPageRes<IssuedCouponRes>>> getCouponList(
                                                                                        @AuthenticationPrincipal UserDetails userDetails,
                                                                                        @RequestParam(name="type", defaultValue = "ISSUED") MemberCouponType memberCouponType,
                                                                                        @RequestParam(name="memberCouponId", required = false) Long memberCouponId,
                                                                                        @RequestParam(name="size", defaultValue = "10")Integer size) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberCouponService.getMemberCouponList(memberId, memberCouponType, memberCouponId, size)));
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
