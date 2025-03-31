package com.appcenter.marketplace.domain.requestMarket.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.requestMarket.dto.res.QRequestMarketRes is a Querydsl Projection type for RequestMarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRequestMarketRes extends ConstructorExpression<RequestMarketRes> {

    private static final long serialVersionUID = 676958231L;

    public QRequestMarketRes(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<Integer> count) {
        super(RequestMarketRes.class, new Class<?>[]{long.class, String.class, String.class, int.class}, id, name, address, count);
    }

}

