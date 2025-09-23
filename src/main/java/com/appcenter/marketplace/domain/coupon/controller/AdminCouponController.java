package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.service.AdminCouponService;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[관리자 쿠폰]", description = "[관리자] 모든 쿠폰 관리 (일반/환급/베타)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/coupons")
public class AdminCouponController {
    private final AdminCouponService adminCouponService;

    // ===== 일반 쿠폰 관리 =====
    @Operation(summary = "전체 일반 쿠폰 조회", description = "관리자가 모든 일반 쿠폰을 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<CouponPageRes<CouponRes>>> getAllCoupons(
            @Parameter(description = "페이지의 마지막 couponId")
            @RequestParam(required = false, name = "lastPageIndex") Long couponId,
            @RequestParam(required = false, name = "marketId") Long marketId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                        adminCouponService.getAllCoupons(couponId, marketId, size)));
    }

    @Operation(summary = "일반 쿠폰 상세 조회", description = "관리자가 특정 일반 쿠폰의 상세 정보를 조회합니다.")
    @GetMapping("/{couponId}")
    public ResponseEntity<CommonResponse<CouponRes>> getCoupon(@PathVariable Long couponId) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                        adminCouponService.getCoupon(couponId)));
    }

    @Operation(summary = "일반 쿠폰 생성", description = "관리자가 일반 쿠폰을 생성합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<CouponRes>> createCoupon(
            @RequestParam(name = "marketId") Long marketId,
            @RequestBody @Valid CouponReq couponReq) {
        return ResponseEntity
                .status(COUPON_CREATE.getStatus())
                .body(CommonResponse.from(COUPON_CREATE.getMessage(),
                        adminCouponService.createCoupon(couponReq, marketId)));
    }

    @Operation(summary = "일반 쿠폰 수정", description = "관리자가 일반 쿠폰을 수정합니다.")
    @PutMapping("/{couponId}")
    public ResponseEntity<CommonResponse<CouponRes>> updateCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid CouponReq couponReq) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_UPDATE.getMessage(),
                        adminCouponService.updateCoupon(couponReq, couponId)));
    }

    @Operation(summary = "일반 쿠폰 숨김/공개 처리", description = "관리자가 일반 쿠폰의 숨김 상태를 변경합니다.")
    @PutMapping("/{couponId}/hidden")
    public ResponseEntity<CommonResponse<Object>> toggleHiddenCoupon(@PathVariable Long couponId) {
        adminCouponService.updateCouponHidden(couponId);
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_HIDDEN.getMessage()));
    }

    @Operation(summary = "일반 쿠폰 삭제", description = "관리자가 일반 쿠폰을 삭제합니다.")
    @DeleteMapping("/{couponId}")
    public ResponseEntity<CommonResponse<Object>> deleteCoupon(@PathVariable Long couponId) {
        adminCouponService.softDeleteCoupon(couponId);
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_DELETE.getMessage()));
    }

    // ===== 환급 쿠폰 관리 =====
    @Operation(summary = "전체 환급 쿠폰 조회", description = "관리자가 모든 환급 쿠폰을 조회합니다.")
    @GetMapping("/payback")
    public ResponseEntity<CommonResponse<CouponPageRes<PaybackRes>>> getAllPaybackCoupons(
            @Parameter(description = "페이지의 마지막 couponId")
            @RequestParam(required = false, name = "lastPageIndex") Long couponId,
            @RequestParam(required = false, name = "marketId") Long marketId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                        adminCouponService.getAllPaybackCoupons(couponId, marketId, size)));
    }

    @Operation(summary = "환급 쿠폰 상세 조회", description = "관리자가 특정 환급 쿠폰의 상세 정보를 조회합니다.")
    @GetMapping("/payback/{couponId}")
    public ResponseEntity<CommonResponse<PaybackRes>> getPaybackCoupon(@PathVariable Long couponId) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                        adminCouponService.getPaybackCoupon(couponId)));
    }

    @Operation(summary = "환급 쿠폰 생성", description = "관리자가 환급 쿠폰을 생성합니다.")
    @PostMapping("/payback")
    public ResponseEntity<CommonResponse<PaybackRes>> createPaybackCoupon(
            @RequestParam(name = "marketId") Long marketId,
            @RequestBody @Valid PaybackReq paybackReq) {
        return ResponseEntity
                .status(COUPON_CREATE.getStatus())
                .body(CommonResponse.from(COUPON_CREATE.getMessage(),
                        adminCouponService.createPaybackCoupon(paybackReq, marketId)));
    }

    @Operation(summary = "환급 쿠폰 수정", description = "관리자가 환급 쿠폰을 수정합니다.")
    @PutMapping("/payback/{couponId}")
    public ResponseEntity<CommonResponse<PaybackRes>> updatePaybackCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid PaybackReq paybackReq) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_UPDATE.getMessage(),
                        adminCouponService.updatePaybackCoupon(paybackReq, couponId)));
    }

    @Operation(summary = "환급 쿠폰 숨김/공개 처리", description = "관리자가 환급 쿠폰의 숨김 상태를 변경합니다.")
    @PutMapping("/payback/{couponId}/hidden")
    public ResponseEntity<CommonResponse<Object>> toggleHiddenPaybackCoupon(@PathVariable Long couponId) {
        adminCouponService.updatePaybackCouponHidden(couponId);
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_HIDDEN.getMessage()));
    }

    @Operation(summary = "환급 쿠폰 삭제", description = "관리자가 환급 쿠폰을 삭제합니다.")
    @DeleteMapping("/payback/{couponId}")
    public ResponseEntity<CommonResponse<Object>> deletePaybackCoupon(@PathVariable Long couponId) {
        adminCouponService.softDeletePaybackCoupon(couponId);
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_DELETE.getMessage()));
    }
}