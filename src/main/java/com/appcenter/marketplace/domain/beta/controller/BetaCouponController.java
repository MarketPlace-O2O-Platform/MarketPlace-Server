package com.appcenter.marketplace.domain.beta.controller;


import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponPageRes;
import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;
import com.appcenter.marketplace.domain.beta.service.BetaCouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;

@Tag(name = "[베타 버전 쿠폰]", description = "[베타] 쿠폰 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beta/coupons")
public class BetaCouponController {
    private final BetaCouponService betaCouponService;

    @Operation(summary = "전체/카테고리 쿠폰 조회 리스트", description = "매장들의 모든 쿠폰을 조회합니다. <br> "+
            "무한 스크롤 방식으로 hasNext가 true 면 마지막 데이터의 betaCouponId를 입력하여, 다음 데이터를 받아올 수 있습니다. <br> " +
            "카테고리 별 매장을 조회하시려면 category도 필요합니다." )
    @GetMapping
    public ResponseEntity<CommonResponse<BetaCouponPageRes<BetaCouponRes>>> getCouponList(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name="betaCouponId", required = false) Long betaCouponId,
            @RequestParam(name = "category", required = false) String major,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(),betaCouponService.getBetaCouponList(memberId, betaCouponId, major, size)));
    }
}
