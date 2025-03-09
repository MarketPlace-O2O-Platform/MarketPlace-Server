package com.appcenter.marketplace.domain.beta.controller;


import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponPageRes;
import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;
import com.appcenter.marketplace.domain.beta.service.BetaCouponService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;
import static com.appcenter.marketplace.global.common.StatusCode.COUPON_USED;

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

    @Operation(summary = "베타 쿠폰 사용처리", description = "회원의 발급 받은 베타 쿠폰을 사용처리 합니다." )
    @PutMapping("/{betaCouponId}")
    public ResponseEntity<CommonResponse<Object>> updateCoupon(
            @PathVariable Long betaCouponId){
        betaCouponService.useBetaCoupon(betaCouponId);
        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage()));
    }
}
