package com.appcenter.marketplace.domain.member_payback.controller;

import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackAdminService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_USED;

@Tag(name = "[관리자 환급 쿠폰 관리]", description = "[관리자] 회원의 환급 쿠폰 관리")
@RestController
@RequestMapping("/api/admin/payback-coupons")
@RequiredArgsConstructor
public class MemberPaybackAdminController {

    private final MemberPaybackAdminService memberPaybackAdminService;

    @Operation(summary = "환급 쿠폰 사용 처리", description = "관리자는 영수증을 토대로, 환급을 진행한 후 사용처리를 완료합니다." )
    @PutMapping("/complete/{couponId}")
    public ResponseEntity<CommonResponse<CouponHandleRes>> manageCoupon(@PathVariable(name = "couponId") Long couponId) {

        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage(),  memberPaybackAdminService.manageCoupon(couponId)));
    }
}
