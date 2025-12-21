package com.appcenter.marketplace.domain.member.service;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberPageRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberSignupDailyStatsRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberSignupStatsRes;

public interface AdminMemberService {

    MemberPageRes<AdminMemberRes> getAllMembers(Long memberId, Integer size, String role);

    AdminMemberRes getMemberDetails(Long memberId);

    void upgradePermission(Long memberId);

    long resetCheerTickets();

    MemberSignupStatsRes getMemberSignupStats();

    MemberSignupDailyStatsRes getMemberSignupDailyStats();
}