package com.appcenter.marketplace.domain.local.repository;

import com.appcenter.marketplace.domain.local.Local;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;

@RequiredArgsConstructor
public class LocalRepositoryCustomImpl implements LocalRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Local findByAdress(String metroGovern, String localGovern) {

        return jpaQueryFactory
                .select(local)
                .from(local)
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(metro.name.eq(metroGovern)
                        .and(local.name.eq(localGovern)))
                .fetchOne();
    }
}
