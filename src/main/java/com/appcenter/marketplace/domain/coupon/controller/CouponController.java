package com.appcenter.marketplace.domain.coupon.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponClosingTopResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponLatestTopResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMarketPageResDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponMemberResDto;
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

@Tag(name="[회원-쿠폰]", description = "[회원] 1개의 매장의 유효한 쿠폰리스트를 조회합니다.")
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

    @Operation(summary = "최신 등록 쿠폰 TOP 5 조회",
            description = "매장별 최신 등록한 쿠폰을 조회합니다. 이때, TOP 10으로 변동해야 할 시, count 로 10을 넣어주시면 됩니다. 기본값은 5개 입니다. <br>" +
                     "'최신 등록'이란, 수정포함입니다.<br>" +
                    " 즉, 사장님이 쿠폰 내용을 수정하거나, 숨김/보기 처리를 하게 되면, 최신 등록 집계에 포함이 됩니다.")
    @GetMapping("/latest/tops")
    public ResponseEntity<CommonResponse<List<CouponLatestTopResDto>>> getLatestTopCouponList(
            @RequestParam(defaultValue = "5", name = "count") Integer size) {
        return ResponseEntity.ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                couponService.getCouponLatestTop(size)));
    }

    @Operation(summary = "최신 등록 쿠폰의 매장 전체 리스트 조회",
            description = "조회 기준은 다음과 같습니다. <br>" +
                    "- 각 매장별 최근에 등록된 쿠폰을 시간순으로 정렬 <br>" +
                    "즉, 가장 최근에 등록된 쿠폰의 매장 순으로 리스트가 조회됩니다. <br> <br>" +
                    "처음 요청 시, pageSize만 보내면 됩니다. (기본값은 5입니다) <br>" +
                    "hasNext가 true일 시, 다음 페이지가 존재합니다.<br>"
    )
    @GetMapping("/latest")
    public ResponseEntity<CommonResponse<CouponMarketPageResDto>> getLatestMarketByCoupon(
            @Parameter(description = "각 페이지의 마지막 couponId (e.g. 5)")
            @RequestParam(required = false, name = "lastPageIndex") Long couponId,
            @Parameter(description = "위에 작성한 couponId의 modifiedAt (e.g. 2024-11-20T00:59:33.469  OR  2024-11-20T00:59:33.469664 )")
            @RequestParam(required = false, name = "lastModifiedAt") LocalDateTime lastModifiedAt,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),
                        couponService.getLatestCouponList(lastModifiedAt, couponId, size)));
    }

    @Operation(summary = "마감 임박 쿠폰 TOP 5 조회",
    description = "매장별 마감 임박학 쿠폰 1개를 조회하여 보여줍니다. <br>" +
            "이때, TOP 10으로 변동해야 할 시, count 로 10을 넣어주시면 됩니다. 기본값은 5개 입니다. <br>" +
            "만약 쿠폰의 마감일자가 같을 시, 최신 등록 매장 순으로 보여지게 됩니다.")
    @GetMapping("/closing")
    public ResponseEntity<CommonResponse<List<CouponClosingTopResDto>>> getClosingTopCouponList(
            @RequestParam(defaultValue = "5", name="count") Integer size){
        return ResponseEntity.ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                couponService.getCouponClosingTop(size)));
    }
  
}
