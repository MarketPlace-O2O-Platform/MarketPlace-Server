package com.appcenter.marketplace.domain.member.service;

import com.appcenter.marketplace.domain.member.dto.req.MemberLoginReq;
import com.appcenter.marketplace.domain.member.dto.res.MemberRes;

public interface MemberService {
    String login(MemberLoginReq memberLoginReq);
    MemberRes getMember(Long studentId);
    long resetCheerTickets();

    void permitAccount(Long memberId,String account, String accountNumber);

    void denyAccount(Long memberId);

    void permitFcm(Long memberId, String fcmToken);
    void denyFcm(Long memberId);
}
