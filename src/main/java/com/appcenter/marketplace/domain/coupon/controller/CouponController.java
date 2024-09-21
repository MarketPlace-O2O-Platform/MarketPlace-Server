package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.domain.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponInfoResDto;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<CouponInfoResDto> create(@RequestParam(name = "marketId")Long id,
                                                   @RequestBody CouponReqDto couponReqDto) {

        Coupon coupon = couponService.createCoupon(couponReqDto, id);

        CouponInfoResDto couponResDto = CouponInfoResDto.toDto(coupon);

        return ResponseEntity.ok(couponResDto);
    }

}
