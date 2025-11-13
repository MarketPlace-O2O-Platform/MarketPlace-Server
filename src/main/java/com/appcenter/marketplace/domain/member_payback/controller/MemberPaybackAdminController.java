package com.appcenter.marketplace.domain.member_payback.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackAdminService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;
import static com.appcenter.marketplace.global.common.StatusCode.COUPON_USED;

@Tag(name = "[관리자 환급 쿠폰 관리]", description = "[관리자] 회원의 환급 쿠폰 관리")
@RestController
@RequestMapping("/api/admins/payback-coupons")
@RequiredArgsConstructor
public class MemberPaybackAdminController {

    private final MemberPaybackAdminService memberPaybackAdminService;

    @Operation(summary = "영수증 제출 내역 조회",
            description = "관리자가 영수증이 제출된 환급 쿠폰 내역을 조회합니다. <br>" +
                    "회원 정보, 쿠폰명, 발급일시, 영수증 제출일시, 영수증 파일명, 환급 여부를 확인할 수 있습니다. <br>" +
                    "처음 요청 시 pageSize만 보내고, 다음 페이지 요청 시 lastMemberPaybackId를 포함하세요. <br>" +
                    "marketId를 보내면 해당 매장의 영수증만 조회됩니다.")
    @GetMapping("/receipts")
    public ResponseEntity<CommonResponse<CouponPageRes<AdminReceiptRes>>> getReceipts(
            @Parameter(description = "마지막 memberPaybackId (커서 페이징용)")
            @RequestParam(required = false, name = "lastMemberPaybackId") Long memberPaybackId,
            @Parameter(description = "매장 ID (특정 매장만 조회 시)")
            @RequestParam(required = false, name = "marketId") Long marketId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {

        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(),
                        memberPaybackAdminService.getReceiptsForAdmin(memberPaybackId, marketId, size)));
    }

    @Operation(summary = "환급 쿠폰 사용 처리", description = "관리자는 영수증을 토대로, 환급을 진행한 후 사용처리를 완료합니다." )
    @PutMapping("/complete/{memberPaybackId}")
    public ResponseEntity<CommonResponse<CouponHandleRes>> manageCoupon(@PathVariable(name = "memberPaybackId") Long memberPaybackId) {

        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage(),  memberPaybackAdminService.manageCoupon(memberPaybackId)));
    }
}
