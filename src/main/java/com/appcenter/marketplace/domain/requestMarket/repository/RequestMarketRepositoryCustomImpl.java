package com.appcenter.marketplace.domain.requestMarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestMarketRepositoryCustomImpl implements RequestMarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

}
