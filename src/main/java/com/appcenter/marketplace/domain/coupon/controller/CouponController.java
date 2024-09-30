package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
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

    

}
