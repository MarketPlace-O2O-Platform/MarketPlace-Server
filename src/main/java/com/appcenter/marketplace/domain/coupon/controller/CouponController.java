package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "유효 쿠폰 조회 리스트", description = "사장님이 공개처리 & 만료되지 않은 쿠폰 리스트가 조회됩니다." +
                                                       "<br> 매장 상세 정보에서 조회가 이루어집니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CouponMemberResDto>>> getCouponList(Long marketId) {
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(CommonResponse.from(COUPON_FOUND.getMessage(),couponService.getCouponList(marketId)));
    }


  
}
