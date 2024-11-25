package com.appcenter.marketplace.domain.local.repository;

import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;

@RequiredArgsConstructor
public class LocalRepositoryCustomImpl implements LocalRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Local findByAdress(String metroGovern, String localGovern) {
        Local OptionalLocal= jpaQueryFactory
                .select(local)
                .from(local)
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(metro.name.eq(metroGovern)
                        .and(local.name.eq(localGovern)))
                .fetchOne();

        if(OptionalLocal==null)
            throw new CustomException(StatusCode.ADDRESS_NOT_EXIST);

        return OptionalLocal;
    }
}
