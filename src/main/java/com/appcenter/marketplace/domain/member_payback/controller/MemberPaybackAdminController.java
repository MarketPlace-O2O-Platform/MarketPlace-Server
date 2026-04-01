package com.appcenter.marketplace.domain.member_payback.controller;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.CouponPaybackStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.RecentMemberPaybackStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.MemberReceiptHistoryRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMarketPaybackRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptSubmissionStatsRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.TopMemberReceiptRes;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackAdminService;

import java.util.List;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.appcenter.marketplace.global.common.StatusCode.*;

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

    @Operation(summary = "영수증 상세 조회",
            description = "관리자가 특정 영수증의 상세 정보를 조회합니다. <br>" +
                    "회원 정보, 쿠폰명, 발급일시, 영수증 제출일시, 영수증 파일명, 환급 여부를 확인할 수 있습니다.")
    @GetMapping("/receipts/{memberPaybackId}")
    public ResponseEntity<CommonResponse<AdminReceiptRes>> getReceiptDetail(
            @Parameter(description = "조회할 memberPaybackId")
            @PathVariable(name = "memberPaybackId") Long memberPaybackId) {

        return ResponseEntity.status(COUPON_FOUND.getStatus())
                .body(CommonResponse.from(COUPON_FOUND.getMessage(),
                        memberPaybackAdminService.getReceiptDetail(memberPaybackId)));
    }

    @Operation(summary = "환급 쿠폰 사용 처리", description = "관리자는 영수증을 토대로, 환급을 진행한 후 사용처리를 완료합니다." )
    @PutMapping("/complete/{memberPaybackId}")
    public ResponseEntity<CommonResponse<CouponHandleRes>> manageCoupon(@PathVariable(name = "memberPaybackId") Long memberPaybackId) {

        return ResponseEntity.status(COUPON_USED.getStatus())
                .body(CommonResponse.from(COUPON_USED.getMessage(),  memberPaybackAdminService.manageCoupon(memberPaybackId)));
    }

    @Operation(summary = "쿠폰 및 환급 통계 조회",
            description = "관리자가 쿠폰 다운로드 및 환급 통계를 조회합니다. <br>" +
                    "한명당 쿠폰 다운수와 쿠폰 다운 대비 환급율을 제공합니다.")
    @GetMapping("/stats")
    public ResponseEntity<CommonResponse<CouponPaybackStatsRes>> getCouponPaybackStats() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getCouponPaybackStats()));
    }

    @Operation(summary = "최근 7일 가입 회원 환급 쿠폰 통계 조회",
            description = "관리자가 최근 7일간 가입한 회원들의 환급 쿠폰 통계를 조회합니다. <br>" +
                    "최근 7일 가입 회원 수와 회원당 평균 환급 쿠폰 다운로드 수를 제공합니다.")
    @GetMapping("/stats/recent")
    public ResponseEntity<CommonResponse<RecentMemberPaybackStatsRes>> getRecentMemberPaybackStats() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getRecentMemberPaybackStats()));
    }

    @Operation(summary = "환급 쿠폰 발급 매장 전체 조회",
            description = "관리자가 회원들이 환급 쿠폰을 발급한 매장 전체를 조회합니다. <br>" +
                    "매장 ID, 매장명, 환급 쿠폰 발급 수를 발급 수 내림차순으로 제공합니다.")
    @GetMapping("/stats/top-markets")
    public ResponseEntity<CommonResponse<List<TopMarketPaybackRes>>> getTopMarketsByPaybackCount() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getTopMarketsByPaybackCount()));
    }

    @Operation(summary = "환급 완료 매장 전체 조회",
            description = "관리자가 영수증 제출 후 환급까지 완료된 건수 기준 매장 전체를 조회합니다. <br>" +
                    "매장 ID, 매장명, 환급 완료 수를 완료 수 내림차순으로 제공합니다.")
    @GetMapping("/stats/top-markets/completed")
    public ResponseEntity<CommonResponse<List<TopMarketPaybackRes>>> getTopMarketsByCompletedPaybackCount() {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getTopMarketsByCompletedPaybackCount()));
    }

    @Operation(summary = "영수증 제출 건수 통계 조회 (기간 필터)",
            description = "기간별 일별 영수증 제출 건수를 조회합니다. <br>" +
                    "period: 7D(최근 7일), 1M(1달), 2M(2달), 3M(3달) <br>" +
                    "startDate + endDate: 직접 날짜 지정 (yyyy-MM-dd) <br>" +
                    "파라미터 없을 시 기본 7D 적용. 날짜 직접 입력이 period보다 우선합니다.")
    @GetMapping("/stats/receipt-submissions")
    public ResponseEntity<CommonResponse<ReceiptSubmissionStatsRes>> getReceiptSubmissionStats(
            @Parameter(description = "기간 (7D | 1M | 2M | 3M)")
            @RequestParam(required = false) String period,
            @Parameter(description = "시작 날짜 (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getReceiptSubmissionStats(period, startDate, endDate)));
    }

    @Operation(summary = "특정 회원 영수증 제출 내역 조회",
            description = "관리자가 학번(memberId)으로 특정 회원의 영수증 제출 내역 전체를 조회합니다. <br>" +
                    "회원의 계좌 정보(1회)와 제출한 영수증(환급 쿠폰) 목록을 함께 반환합니다.")
    @GetMapping("/receipts/members/{memberId}")
    public ResponseEntity<CommonResponse<MemberReceiptHistoryRes>> getReceiptHistoryByMemberId(
            @Parameter(description = "조회할 회원 학번(memberId)")
            @PathVariable(name = "memberId") Long memberId) {
        return ResponseEntity
                .ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                        memberPaybackAdminService.getReceiptHistoryByMemberId(memberId)));
    }

    @Operation(summary = "영수증 제출 횟수 회원 전체 조회",
            description = "관리자가 영수증을 제출한 회원 전체를 제출 횟수 내림차순으로 조회합니다. <br>" +
                    "period 파라미터로 기간 필터 적용 가능합니다. <br>" +
                    "DAY(하루), WEEK(일주일), MONTH(한달) / 미입력 시 전체 기간")
    @GetMapping("/stats/top-members/receipt")
    public ResponseEntity<CommonResponse<List<TopMemberReceiptRes>>> getTopMembersByReceiptCount(
            @Parameter(description = "기간 필터 (DAY | WEEK | MONTH), 미입력 시 전체")
            @RequestParam(required = false) String period) {
        return ResponseEntity
                .ok(CommonResponse.from(STATS_FOUND.getMessage(),
                        memberPaybackAdminService.getTopMembersByReceiptCount(period)));
    }
}
