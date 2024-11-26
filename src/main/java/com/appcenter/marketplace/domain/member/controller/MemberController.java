package com.appcenter.marketplace.domain.member.controller;


import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReqDto;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginResDto;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.MEMBER_FOUND;
import static com.appcenter.marketplace.global.common.StatusCode.MEMBER_LOGIN_SUCCESS;

@Tag(name = "[회원]", description = "[회원] 로그인 및 학번 조회")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "학생 로그인", description = "포털 시스템 정보 (학번, 비밀번호)로 로그인합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<MemberLoginResDto>> loginMember(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto) {
        return ResponseEntity.status(MEMBER_LOGIN_SUCCESS.getStatus())
                .body(CommonResponse.from(MEMBER_LOGIN_SUCCESS.getMessage(),memberService.login(memberLoginReqDto)));
    }

    @Operation(summary = "학생 학번 조회", description = "마이페이지에 로그인한 회원의 학번을 조회하기 위해 사용됩니다." )
    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponse<MemberLoginResDto>> getMember(@PathVariable Long memberId){
        return ResponseEntity.status(MEMBER_FOUND.getStatus())
                .body(CommonResponse.from(MEMBER_FOUND.getMessage(),memberService.getMember(memberId)));
    }
}
