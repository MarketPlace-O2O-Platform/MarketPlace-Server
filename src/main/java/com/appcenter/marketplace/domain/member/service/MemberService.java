package com.appcenter.marketplace.domain.member.service;

import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReqDto;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginResDto;

public interface MemberService {
    MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto);
    MemberLoginResDto getMember(Long studentId);
}
