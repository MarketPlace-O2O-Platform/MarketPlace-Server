package com.appcenter.marketplace.domain.member.service;

import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberLoginRes;

public interface MemberService {
    MemberLoginRes login(MemberLoginReq memberLoginReq);
    MemberLoginRes getMember(Long studentId);
    long resetCheerTickets();

    void permitFcm(Long memberId, String fcmToken);
    void denyFcm(Long memberId);
}
