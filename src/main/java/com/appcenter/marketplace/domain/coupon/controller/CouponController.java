package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;

@Tag(name = "[쿠폰]", description = "[사장님,회원] 매장 별 쿠폰 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "유효 쿠폰 조회 리스트", description = "사장님이 공개처리 & 만료되지 않은 쿠폰 리스트가 조회됩니다. <br>" +
                                                     "매장 상세 정보에서 조회가 이루어집니다. <br> " +
            "couponId는 다음 페이징 처리를 위해 사용되는 파라미터 입니다. 다음")
    @GetMapping
    public ResponseEntity<CommonResponse<CouponPageRes<CouponMemberRes>>> getCouponList(
            @RequestParam(name= "marketId")Long marketId,
            @RequestParam(name="couponId", required = false) Long couponId,
            @RequestParam(name="size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(),couponService.getCouponList(marketId, couponId, size)));
    }


  
}
