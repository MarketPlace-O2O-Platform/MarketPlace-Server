package com.appcenter.marketplace.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.appcenter.marketplace.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public long resetCheerTickets() {
        return jpaQueryFactory
                .update(member)
                .set(member.cheerTicket, 1)
                .execute();
    }
}
