package com.appcenter.marketplace.domain.member_payback.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[회원 환급 쿠폰(발급)]", description = "[회원] 회원의 환급 쿠폰의 발급 처리 및 리스트 조회")
@RestController
@RequestMapping("/api/members/payback-coupons")
@RequiredArgsConstructor
public class MemberPaybackController {

    private final MemberPaybackService memberPaybackService;

    @Operation(summary = "회원 환급 쿠폰 발급", description = "회원은 해당 couponId로 유효한 쿠폰을 발급받을 수 있습니다. " +
            "<br> '유효한 쿠폰'은 공개처리가 된 쿠폰 및 만료되지 않은 쿠폰을 뜻합니다." )
    @PostMapping("/{couponId}")
    public ResponseEntity<CommonResponse<Object>> issuedCoupon(@AuthenticationPrincipal UserDetails userDetails,
                                                               @PathVariable(name="couponId") Long paybackId) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberPaybackService.issuedCoupon(memberId, paybackId);
        return ResponseEntity.status(COUPON_ISSUED.getStatus()).body(CommonResponse.from(COUPON_ISSUED.getMessage()));
    }

    @Operation(summary = "회원의 쿠폰 리스트", description = "type은 'ISSUED(사용가능한)', 'ENDED(끝난)' 중에서 작성해주시면 됩니다. <br>" +
            "쿠폰 리스트를 조회하면 기본적으로 조회되는 리스트는 ISSUED로 사용가능한 쿠폰 리스트입니다.<br> " +
            "무한 스크롤 방식으로 hasNext가 true 면 마지막 데이터의 memberCouponId를 입력하여, 다음 페이지==다음 데이터를 받아올 수 있습니다." )
    @GetMapping
    public ResponseEntity<CommonResponse<CouponPageRes<IssuedCouponRes>>> getCouponList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name="type", defaultValue = "ISSUED") MemberCouponType memberCouponType,
            @RequestParam(name= "memberCouponId", required = false) Long memberPaybackId,
            @RequestParam(name="size", defaultValue = "10")Integer size) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(), memberPaybackService.getPaybackCouponList(memberId, memberCouponType, memberPaybackId, size)));
    }

    @Operation(summary = "회원 쿠폰 영수증 제출", description = "회원은 발급받은 memberCouponId로 영수증을 제출합니다." )
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<CouponHandleRes>> updateCoupon(@AuthenticationPrincipal UserDetails userDetails,
                                                                        @RequestParam(name= "memberCouponId") Long memberPaybackId,
                                                                        @RequestPart(value = "image") MultipartFile image) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(RECEIPT_SUBMIT.getStatus())
                .body(CommonResponse.from(RECEIPT_SUBMIT.getMessage(), memberPaybackService.updateCoupon(memberId, memberPaybackId, image)));
    }

    @Operation(summary = "회원 쿠폰 영수증 조회", description = "회원은 제출한 영수증을 조회할 수 있습니다. \n" +
            "이미지를 조회할 땐, \"/image/receipt/\" prefix를 꼭 붙여야 합니다. ")

    @GetMapping("/receipt/{memberCouponId}")
    public ResponseEntity<CommonResponse<ReceiptRes>> getReceipt(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable(name = "memberCouponId") Long memberPaybackId) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(
                CommonResponse.from(COUPON_FOUND.getMessage(), memberPaybackService.getReceipt(memberId, memberPaybackId))
        );
    }
}
