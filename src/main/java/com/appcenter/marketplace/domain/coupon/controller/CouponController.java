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
    public ResponseEntity<CouponResDto> create(@RequestParam(name = "marketId")Long id,
                                                @RequestBody CouponReqDto couponReqDto) {

        CouponResDto resDto = couponService.createCoupon(couponReqDto, id);

        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

}
