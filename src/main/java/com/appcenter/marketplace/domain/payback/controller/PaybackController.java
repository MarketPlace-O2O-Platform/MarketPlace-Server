package com.appcenter.marketplace.domain.payback.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.payback.dto.res.PaybackRes;
import com.appcenter.marketplace.domain.payback.service.PaybackService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;

@Tag(name = "[회원 환급 쿠폰]", description = "[회원] 매장별 환급 쿠폰 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaybackController {

    private final PaybackService paybackService;

    @Operation(summary = "매장별 전체 생성된 환급 쿠폰 조회", description = "특정 매장(MarketId)의 전체 쿠폰 리스트를 조회합니다.")
    @GetMapping("/payback-coupons")
    public ResponseEntity<CommonResponse<CouponPageRes<PaybackRes>>> getCouponList(
            Authentication authentication,
            @RequestParam(name = "marketId")Long marketId,
            @RequestParam(name = "couponId", required = false) Long couponId,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        Long memberId = extractMemberId(authentication);
        return ResponseEntity.status(COUPON_FOUND.getStatus()).body(CommonResponse.from(COUPON_FOUND.getMessage(),paybackService.getCouponListForMembers(marketId, memberId, couponId, size)));
    }

    private Long extractMemberId(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Long.parseLong(userDetails.getUsername());
        }
        return null;
    }
}
