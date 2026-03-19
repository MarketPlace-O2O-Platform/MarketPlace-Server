package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_payback.dto.res.QTopMarketPaybackRes is a Querydsl Projection type for TopMarketPaybackRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopMarketPaybackRes extends ConstructorExpression<TopMarketPaybackRes> {

    private static final long serialVersionUID = 1646302659L;

    public QTopMarketPaybackRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<Long> paybackCount) {
        super(TopMarketPaybackRes.class, new Class<?>[]{long.class, String.class, long.class}, marketId, marketName, paybackCount);
    }

}

