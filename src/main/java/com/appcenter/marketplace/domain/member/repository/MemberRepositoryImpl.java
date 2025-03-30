package com.appcenter.marketplace.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
}
