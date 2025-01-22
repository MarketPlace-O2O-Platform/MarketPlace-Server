package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.ClosingCouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponRes;
import com.appcenter.marketplace.domain.coupon.dto.res.LatestCouponRes;
import com.appcenter.marketplace.domain.coupon.service.CouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@Tag(name = "[쿠폰]", description = "[사장님,회원] 매장 별 쿠폰 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "유효 쿠폰 조회 리스트", description = "사장님이 공개처리 & 만료되지 않은 쿠폰 리스트가 조회됩니다. <br>" +
                                                     "매장 상세 정보에서 조회가 이루어집니다. <br> " +
            "couponId는 다음 페이징 처리를 위해 사용되는 파라미터 입니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<CouponPageRes<CouponRes>>> getCouponList(
            @RequestParam(name= "marketId")Long marketId,
            @RequestParam(name="couponId", required = false) Long couponId,
            @RequestParam(name="size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(),couponService.getCouponList(marketId, couponId, size)));
    }

    @Operation(summary = "최신 등록 쿠폰 조회",
            description = "최신 쿠폰을 등록순으로 정렬 <br>" +
                    "처음 요청 시, pageSize만 보내면 됩니다. (기본값은 10입니다) <br>"
    )
    @GetMapping("/latest")
    public ResponseEntity<CommonResponse<CouponPageRes<LatestCouponRes>>> getLatestMarketByCoupon(
            @Parameter(description = "위에 작성한 marketId의 createdAt (e.g. 2024-11-20T00:59:33.469  OR  2024-11-20T00:59:33.469664 )")
            @RequestParam(required = false, name = "lastCreatedAt") LocalDateTime lastCreatedAt,
            @Parameter(description = "각 페이지의 마지막 couponId (e.g. 5)")
            @RequestParam(required = false, name = "lastCouponId") Long couponId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),
                        couponService.getLatestCouponPage(lastCreatedAt, couponId,size)));
    }

    @Operation(summary = "마감 임박 쿠폰 리스트",
            description = "마감 임박이 다가온 쿠폰을 조회합니다. <br>" +
                    "처음 요청 시엔 pageSize만 필요합니다. 기본값은 10입니다. <br>" +
                    "만약 쿠폰의 마감일자가 같을 시, 최신 등록 매장 순으로 보여지게 됩니다.")
    @GetMapping("/closing")
    public ResponseEntity<CommonResponse<List<ClosingCouponRes>>> getClosingTopCouponList(
            @RequestParam(defaultValue = "10", name="pageSize") Integer size){
        return ResponseEntity.ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                couponService.getClosingCouponPage(size)));
    }

  
}
