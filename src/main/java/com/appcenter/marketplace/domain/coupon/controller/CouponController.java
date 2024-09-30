package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponHiddenReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "사장님 쿠폰 생성", description = "사장님이 1개의 쿠폰을 생성합니다.")
    @PostMapping("/coupons")
    public ResponseEntity<CouponResDto> createCoupon(@RequestParam(name = "marketId")Long id,
                                                @RequestBody CouponReqDto couponReqDto) {

        return ResponseEntity.status(HttpStatus.OK).body(couponService.createCoupon(couponReqDto, id));
    }

    @Operation(summary = "사장님 쿠폰 단일 조회", description = "사장님이 생성한 1개의 쿠폰을 조회합니다.")
    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResDto> getCoupon(@PathVariable Long couponId) {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.getCoupon(couponId));
    }

    @Operation(summary = "사장님 쿠폰 내용 수정", description = "사장님이 생성한 쿠폰의 내용을 수정합니다. " +
                                                  "<br> '숨김처리'를 제외한 내용을 수정할 수 있습니다. ")
    @PutMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResDto> updateCoupon(@RequestBody CouponUpdateReqDto couponUpdateReqDto,
                                                        @PathVariable Long couponId ){
        return ResponseEntity.status(HttpStatus.OK).body(couponService.updateCoupon(couponUpdateReqDto, couponId));
    }

    @Operation(summary = "숨김/공개 처리 기능", description = "사장님은 생성한 쿠폰을 숨김 / 공개 처리 할 수 있습니다.")
    @PutMapping("/coupons/hidden/{couponId}")
    public ResponseEntity<CouponHiddenResDto> hiddenCoupon(@RequestBody CouponHiddenReqDto couponHiddenReqDto,
                                                           @PathVariable Long couponId) {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.updateCouponHidden(couponHiddenReqDto, couponId));
    }

    @Operation(summary = "사장님 쿠폰 삭제", description = "사장님은 쿠폰을 삭제할 수 있습니다. " +
                                        "<br> 삭제는 소프트 딜리트로 구현됩니다.")
    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
