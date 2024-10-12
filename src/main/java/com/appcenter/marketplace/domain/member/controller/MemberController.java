package com.appcenter.marketplace.domain.member.controller;


import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReqDto;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginResDto;
import com.appcenter.marketplace.domain.member.service.MemberService;
import com.appcenter.marketplace.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CommonResponse<MemberLoginResDto>> loginMember(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        return ResponseEntity.status(MEMBER_LOGIN_SUCCESS.getStatus())
                .body(CommonResponse.from(MEMBER_LOGIN_SUCCESS.getMessage(),memberService.login(memberLoginReqDto)));
    }
}
