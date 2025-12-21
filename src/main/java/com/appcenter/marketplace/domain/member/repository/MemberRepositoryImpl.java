package com.appcenter.marketplace.domain.member.repository;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.appcenter.marketplace.domain.member.Role;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long resetCheerTickets() {
        return jpaQueryFactory
                .update(member)
                .set(member.cheerTicket, 3) // 공감권 3개로 수정 완료 ( 배포 시)
                .execute();
    }

    @Override
    public List<AdminMemberRes> findMembersForAdmin(Long memberId, Integer size, String role) {
        return jpaQueryFactory
                .select(Projections.constructor(AdminMemberRes.class,
                        member.id,
                        member.cheerTicket,
                        member.account,
                        member.accountNumber,
                        member.role,
                        member.fcmToken,
                        member.createdAt,
                        member.modifiedAt))
                .from(member)
                .where(ltMemberId(memberId)
                        .and(eqRole(role)))
                .orderBy(member.id.desc())
                .limit(size + 1)
                .fetch();
    }

    private BooleanBuilder ltMemberId(Long memberId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (memberId != null) {
            builder.and(member.id.lt(memberId));
        }
        return builder;
    }

    private BooleanBuilder eqRole(String role) {
        BooleanBuilder builder = new BooleanBuilder();
        if (role != null && Role.exists(role)) {
            builder.and(member.role.eq(Role.valueOf(role)));
        }
        return builder;
    }

    @Override
    public long countByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Long count = jpaQueryFactory
                .select(member.count())
                .from(member)
                .where(member.createdAt.between(startDateTime, endDateTime))
                .fetchOne();
        return count != null ? count : 0L;
    }
}
