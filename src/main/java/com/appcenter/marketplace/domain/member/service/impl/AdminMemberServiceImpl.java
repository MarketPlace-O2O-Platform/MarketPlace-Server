package com.appcenter.marketplace.domain.member.service.impl;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.dto.res.DailySignupStats;
import com.appcenter.marketplace.domain.member.dto.res.MemberPageRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberSignupDailyStatsRes;
import com.appcenter.marketplace.domain.member.dto.res.MemberSignupStatsRes;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member.service.AdminMemberService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Override
    public MemberSignupStatsRes getMemberSignupStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(today, LocalTime.MAX);

        // 하루 가입수 (오늘)
        long todaySignupCount = memberRepository.countByCreatedAtBetween(todayStart, todayEnd);

        // 최근 7일 가입수
        LocalDateTime sevenDaysAgoStart = LocalDateTime.of(today.minusDays(7), LocalTime.MIN);
        long recentSevenDaysCount = memberRepository.countByCreatedAtBetween(sevenDaysAgoStart, todayEnd);

        // 그 전 7일 가입수 (14일 전 ~ 7일 전)
        LocalDateTime fourteenDaysAgoStart = LocalDateTime.of(today.minusDays(14), LocalTime.MIN);
        LocalDateTime sevenDaysAgoEnd = LocalDateTime.of(today.minusDays(7), LocalTime.MAX);
        long previousSevenDaysCount = memberRepository.countByCreatedAtBetween(fourteenDaysAgoStart, sevenDaysAgoEnd);

        // 7일 가입 변화량
        long sevenDayChangeCount = recentSevenDaysCount - previousSevenDaysCount;

        return MemberSignupStatsRes.of(todaySignupCount, sevenDayChangeCount);
    }

    @Override
    public MemberSignupDailyStatsRes getMemberSignupDailyStats() {
        LocalDate today = LocalDate.now();
        List<DailySignupStats> dailyStatsList = new ArrayList<>();

        // 7일 전부터 오늘까지 각 날짜별 가입 수 계산
        for (int daysAgo = 7; daysAgo >= 0; daysAgo--) {
            LocalDate targetDate = today.minusDays(daysAgo);
            LocalDateTime dayStart = LocalDateTime.of(targetDate, LocalTime.MIN);
            LocalDateTime dayEnd = LocalDateTime.of(targetDate, LocalTime.MAX);

            long signupCount = memberRepository.countByCreatedAtBetween(dayStart, dayEnd);
            dailyStatsList.add(DailySignupStats.of(targetDate, signupCount));
        }

        return MemberSignupDailyStatsRes.of(dailyStatsList);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private <T> MemberPageRes<T> checkNextPageAndReturn(List<T> memberList, Integer size) {
        boolean hasNext = memberList.size() > size;

        if (hasNext) {
            memberList = memberList.subList(0, size);
        }

        return new MemberPageRes<>(memberList, hasNext);
    }
}