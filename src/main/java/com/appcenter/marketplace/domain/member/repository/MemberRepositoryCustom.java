package com.appcenter.marketplace.domain.member.repository;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;

import java.util.List;

public interface MemberRepositoryCustom {
    long resetCheerTickets();

    List<AdminMemberRes> findMembersForAdmin(Long memberId, Integer size);
}
