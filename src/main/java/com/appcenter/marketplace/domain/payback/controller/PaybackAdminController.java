package com.appcenter.marketplace.domain.payback.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.service.PaybackService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[관리자/사장님 환급 쿠폰]", description = "[관리자, 사장님] 환급 쿠폰 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class PaybackAdminController {
    private final PaybackService paybackAdminService;

    @Operation(summary = "환급형 쿠폰 생성", description = "관리자(혹은 사장님)이 1개의 환급형 쿠폰을 생성합니다. ")
    @PostMapping("/payback-coupons")
    public ResponseEntity<CommonResponse<PaybackRes>> createCoupon(@RequestParam(name = "marketId")Long marketId,
                                                                   @RequestBody @Valid PaybackReq req) {
        return ResponseEntity.status(COUPON_CREATE.getStatus()).body(CommonResponse.from(COUPON_CREATE.getMessage(), paybackAdminService.createCoupon(req, marketId)));
    }

    @Operation(summary = "매장별 전체 생성된 환급 쿠폰 조회", description = "특정 매장(MarketId)의 전체 쿠폰 리스트를 조회합니다.")
    @GetMapping("/payback-coupons")
    public ResponseEntity<CommonResponse<CouponPageRes<PaybackRes>>> getCouponList(@RequestParam(name = "marketId")Long marketId,
                                                                                  @RequestParam(name = "couponId", required = false) Long couponId,
                                                                                  @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(CommonResponse.from(COUPON_FOUND.getMessage(),paybackAdminService.getCouponListForAdmin(marketId, couponId, size)));
    }


    @Operation(summary = "쿠폰 내용 수정", description = "관리자(혹은 사장님)이 생성한 쿠폰의 내용을 수정합니다. " +
            "<br> '숨김처리'를 제외한 쿠폰 제목/내용을 수정할 수 있습니다. ")
    @PutMapping("/payback-coupons/{couponId}")
    public ResponseEntity<CommonResponse<PaybackRes>> updateCoupon(@RequestBody @Valid PaybackReq req,
                                                                  @PathVariable Long couponId ){
        return ResponseEntity.status(COUPON_UPDATE.getStatus()).body(CommonResponse.from(COUPON_UPDATE.getMessage(),paybackAdminService.updateCoupon(req, couponId)));
    }

    @Operation(summary = "숨김/공개 처리 기능", description = "생성한 쿠폰을 숨김 / 공개 처리 할 수 있습니다.")
    @PutMapping("/payback-coupons/hidden/{couponId}")
    public ResponseEntity<CommonResponse<Object>> hiddenCoupon(@PathVariable Long couponId) {
        paybackAdminService.updateCouponHidden(couponId);
        return ResponseEntity.status(COUPON_HIDDEN.getStatus()).body(CommonResponse.from(COUPON_HIDDEN.getMessage()));
    }

    @Operation(summary = "관리자(혹은 사장님) 쿠폰 삭제", description = "관리자(혹은 사장님)은 쿠폰을 삭제할 수 있습니다. " +
            "<br> 삭제는 소프트 딜리트로 구현됩니다.")
    @DeleteMapping("/payback-coupons/{couponId}")
    public ResponseEntity<CommonResponse<Object>> deleteCoupon(@PathVariable Long couponId) {
        paybackAdminService.softDeleteCoupon(couponId);
        return ResponseEntity.status(COUPON_DELETE.getStatus()).body(CommonResponse.from(COUPON_DELETE.getMessage()));
    }
}
