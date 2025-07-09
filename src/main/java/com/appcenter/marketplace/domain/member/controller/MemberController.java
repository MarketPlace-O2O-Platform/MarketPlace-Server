package com.appcenter.marketplace.domain.member.controller;


import com.appcenter.marketplace.domain.member.dto.req.MemberAccountReq;
import com.appcenter.marketplace.domain.member.dto.req.MemberFcmReq;
import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberRes;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Tag(name = "[회원]", description = "[회원] 로그인 및 학번 조회")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "학생 로그인", description = "포털 시스템 정보 (학번, 비밀번호)로 로그인합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<String>> loginMember(@RequestBody @Valid MemberLoginReq memberLoginReq) {
        return ResponseEntity.status(MEMBER_LOGIN_SUCCESS.getStatus())
                .body(CommonResponse.from(MEMBER_LOGIN_SUCCESS.getMessage(),memberService.login(memberLoginReq)));
    }

    @Operation(summary = "학생 조회", description = "마이페이지에 로그인한 회원의 정보를 조회하기 위해 사용됩니다." )
    @GetMapping
    public ResponseEntity<CommonResponse<MemberRes>> getMember(@AuthenticationPrincipal UserDetails userDetails){
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.status(MEMBER_FOUND.getStatus())
                .body(CommonResponse.from(MEMBER_FOUND.getMessage(),memberService.getMember(memberId)));
    }

    @Operation(summary = "주거래은행, 계좌번호 저장 API", description = "환급 쿠폰을 사용하기 위한 정보를 저장합니다.")
    @PatchMapping("/account/permit")
    public ResponseEntity<CommonResponse<Object>> permitAccount(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MemberAccountReq memberAccountReq) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.permitAccount(memberId, memberAccountReq.getAccount(),memberAccountReq.getAccountNumber());
        return ResponseEntity.status(MEMBER_ACCOUNT_PERMIT.getStatus())
                .body(CommonResponse.from(MEMBER_ACCOUNT_PERMIT.getMessage()));
    }

    @Operation(summary = "주거래은행, 계좌번호 삭제 API", description = "환급 쿠폰을 사용하기 위한 정보를 삭제합니다.")
    @PatchMapping("/account/deny")
    public ResponseEntity<CommonResponse<Object>> denyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.denyAccount(memberId);
        return ResponseEntity.status(MEMBER_ACCOUNT_DENY.getStatus())
                .body(CommonResponse.from(MEMBER_ACCOUNT_DENY.getMessage()));
    }

    @Operation(summary = "FCM 토큰 저장 API", description = "FCM 토큰을 저장합니다.")
    @PatchMapping("/notification/permit")
    public ResponseEntity<CommonResponse<Object>> permitFcmToken(@AuthenticationPrincipal UserDetails userDetails,@RequestBody MemberFcmReq memberFcmReq) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.permitFcm(memberId, memberFcmReq.getFcmToken());
        return ResponseEntity.status(MEMBER_FCM_PERMIT.getStatus())
                .body(CommonResponse.from(MEMBER_FCM_PERMIT.getMessage()));
    }

    @Operation(summary = "FCM 토큰 삭제 API", description = "FCM 토큰을 삭제합니다.")
    @PatchMapping("/notification/deny")
    public ResponseEntity<CommonResponse<Object>> denyFcmToken(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.denyFcm(memberId);
        return ResponseEntity.status(MEMBER_FCM_DENY.getStatus())
                .body(CommonResponse.from(MEMBER_FCM_DENY.getMessage()));
    }

    @Operation(summary = "관리자 권한 승급", description = "해당 회원을 관리자로 임명합니다.")
    @PatchMapping("/admin")
    public ResponseEntity<CommonResponse<Object>> upgradePermission(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.upgradePermission(memberId);
        return ResponseEntity.status(MEMBER_UPGRADE_PERMISSION.getStatus())
                .body(CommonResponse.from(MEMBER_UPGRADE_PERMISSION.getMessage()));
    }
}
