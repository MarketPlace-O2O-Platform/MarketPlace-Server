package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponHiddenReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponResDto;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<CouponResDto> createCoupon(@RequestParam(name = "marketId")Long id,
                                                @RequestBody CouponReqDto couponReqDto) {

        return ResponseEntity.status(HttpStatus.OK).body(couponService.createCoupon(couponReqDto, id));
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResDto> getCoupon(@PathVariable Long couponId) {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.getCoupon(couponId));
    }

    @PutMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResDto> updateCoupon(@RequestBody CouponUpdateReqDto couponUpdateReqDto,
                                                        @PathVariable Long couponId ){
        return ResponseEntity.status(HttpStatus.OK).body(couponService.updateCoupon(couponUpdateReqDto, couponId));
    }

    @PutMapping("/coupons/hidden/{couponId}")
    public ResponseEntity<CouponHiddenResDto> hiddenCoupon(@RequestBody CouponHiddenReqDto couponHiddenReqDto,
                                                           @PathVariable Long couponId) {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.updateCouponHidden(couponHiddenReqDto, couponId));
    }

    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
