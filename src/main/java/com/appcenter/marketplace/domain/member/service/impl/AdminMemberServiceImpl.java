package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberPageRes;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member.service.AdminMemberService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberPageRes<AdminMemberRes> getAllMembers(Long memberId, Integer size, String role) {
        List<AdminMemberRes> memberList = memberRepository.findMembersForAdmin(memberId, size, role);
        return checkNextPageAndReturn(memberList, size);
    }

    @Override
    public AdminMemberRes getMemberDetails(Long memberId) {
        Member member = findMemberById(memberId);
        return AdminMemberRes.toDto(member);
    }

    @Override
    @Transactional
    public void upgradePermission(Long memberId) {
        Member member = findMemberById(memberId);
        member.upgradePermission();
    }

    @Override
    @Transactional
    public long resetCheerTickets() {
        return memberRepository.resetCheerTickets();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private <T> MemberPageRes<T> checkNextPageAndReturn(List<T> memberList, Integer size) {
        boolean hasNext = false;

        if (memberList.size() > size) {
            hasNext = true;
            memberList.remove(size.intValue());
        }

        return new MemberPageRes<>(memberList, hasNext);
    }
}