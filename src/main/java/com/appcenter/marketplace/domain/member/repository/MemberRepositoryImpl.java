package com.appcenter.marketplace.domain.member.repository;

import com.appcenter.marketplace.domain.member.dto.res.AdminMemberRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long resetCheerTickets() {
        return jpaQueryFactory
                .update(member)
                .set(member.cheerTicket, 1000) //1로 수정 예정
                .execute();
    }

    @Override
    public List<AdminMemberRes> findMembersForAdmin(Long memberId, Integer size) {
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
                .where(ltMemberId(memberId))
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
}
