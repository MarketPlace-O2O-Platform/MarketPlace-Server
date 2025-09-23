package com.appcenter.marketplace.domain.member.service;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberPageRes;

public interface AdminMemberService {

    MemberPageRes<AdminMemberRes> getAllMembers(Long memberId, Integer size);

    AdminMemberRes getMemberDetails(Long memberId);

    void upgradePermission(Long memberId);

    long resetCheerTickets();
}