package com.appcenter.marketplace.domain.member.controller;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberPageRes;
import com.appcenter.marketplace.domain.member.service.AdminMemberService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[관리자 회원]", description = "[관리자] 회원 전체 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/members")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @Operation(summary = "전체 회원 목록 조회", description = "관리자가 전체 회원 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<MemberPageRes<AdminMemberRes>>> getAllMembers(
            @Parameter(description = "페이지의 마지막 memberId")
            @RequestParam(required = false, name = "lastPageIndex") Long memberId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size,
            @Parameter(description = "권한별 필터 (USER, ADMIN)")
            @RequestParam(required = false, name = "role") String role) {
        return ResponseEntity
                .ok(CommonResponse.from(MEMBER_FOUND.getMessage(),
                        adminMemberService.getAllMembers(memberId, size, role)));
    }

    @Operation(summary = "회원 상세 조회", description = "관리자가 특정 회원의 상세 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponse<AdminMemberRes>> getMember(@PathVariable Long memberId) {
        return ResponseEntity
                .ok(CommonResponse.from(MEMBER_FOUND.getMessage(),
                        adminMemberService.getMemberDetails(memberId)));
    }

    @Operation(summary = "회원 권한 승급", description = "관리자가 회원을 관리자로 승급시킵니다.")
    @PatchMapping("/{memberId}/upgrade")
    public ResponseEntity<CommonResponse<Object>> upgradePermission(@PathVariable Long memberId) {
        adminMemberService.upgradePermission(memberId);
        return ResponseEntity
                .ok(CommonResponse.from(MEMBER_UPGRADE_PERMISSION.getMessage()));
    }

    @Operation(summary = "회원 응원티켓 초기화", description = "관리자가 모든 회원의 응원티켓을 초기화합니다.")
    @PatchMapping("/reset-tickets")
    public ResponseEntity<CommonResponse<Object>> resetCheerTickets() {
        long resetCount = adminMemberService.resetCheerTickets();
        return ResponseEntity
                .ok(CommonResponse.from("총 " + resetCount + "명의 응원티켓이 초기화되었습니다."));
    }
}