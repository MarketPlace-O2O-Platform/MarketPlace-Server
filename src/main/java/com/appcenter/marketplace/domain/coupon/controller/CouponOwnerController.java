package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.service.CouponOwnerService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;


@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class CouponOwnerController {

    private final CouponOwnerService couponService;

    @Operation(summary = "사장님 쿠폰 생성", description = "사장님이 1개의 쿠폰을 생성합니다.")
    @PostMapping("/coupons")
    public ResponseEntity<CommonResponse<CouponResDto>> createCoupon(@RequestParam(name = "marketId")Long id,
                                                                     @RequestBody @Valid CouponReqDto couponReqDto) {

        return ResponseEntity.status(COUPON_CREATE.getStatus()).body(CommonResponse.from(COUPON_CREATE.getMessage(),couponService.createCoupon(couponReqDto, id)));
    }

    @Operation(summary = "사장님 쿠폰 단일 조회", description = "사장님이 생성한 1개의 쿠폰을 조회합니다.")
    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CommonResponse<CouponResDto>> getCoupon(@PathVariable Long couponId) {
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(CommonResponse.from(COUPON_FOUND.getMessage(),couponService.getCoupon(couponId)));
    }

    @Operation(summary = "사장님 매장별 전체 쿠폰 조회", description = "사장님의 특정 매장(MarketId)의 전체 쿠폰 리스트를 조회합니다.")
    @GetMapping("/coupons")
    public ResponseEntity<CommonResponse<List<CouponResDto>>> getCouponList(@RequestParam(name= "marketId")Long id) {
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(CommonResponse.from(COUPON_FOUND.getMessage(),couponService.getCouponList(id)));
    }

    @Operation(summary = "사장님 쿠폰 내용 수정", description = "사장님이 생성한 쿠폰의 내용을 수정합니다. " +
                                                  "<br> '숨김처리'를 제외한 내용을 수정할 수 있습니다. ")
    @PutMapping("/coupons/{couponId}")
    public ResponseEntity<CommonResponse<CouponResDto>> updateCoupon(@RequestBody @Valid CouponUpdateReqDto couponUpdateReqDto,
                                                        @PathVariable Long couponId ){
        return ResponseEntity.status(COUPON_UPDATE.getStatus()).body(CommonResponse.from(COUPON_UPDATE.getMessage(),couponService.updateCoupon(couponUpdateReqDto, couponId)));
    }

    @Operation(summary = "숨김/공개 처리 기능", description = "사장님은 생성한 쿠폰을 숨김 / 공개 처리 할 수 있습니다.")
    @PutMapping("/coupons/hidden/{couponId}")
    public ResponseEntity<CommonResponse<CouponHiddenResDto>> hiddenCoupon(@PathVariable Long couponId) {
        return ResponseEntity.status(COUPON_HIDDEN.getStatus()).body(CommonResponse.from(COUPON_HIDDEN.getMessage(),couponService.updateCouponHidden(couponId)));
    }

    @Operation(summary = "사장님 쿠폰 삭제", description = "사장님은 쿠폰을 삭제할 수 있습니다. " +
                                        "<br> 삭제는 소프트 딜리트로 구현됩니다.")
    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<CommonResponse<Object>> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.status(COUPON_DELETE.getStatus()).body(CommonResponse.from(COUPON_DELETE.getMessage()));
    }
}
