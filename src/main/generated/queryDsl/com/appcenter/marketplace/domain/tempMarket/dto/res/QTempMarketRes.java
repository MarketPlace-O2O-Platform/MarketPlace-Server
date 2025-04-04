package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.tempMarket.dto.res.QTempMarketRes is a Querydsl Projection type for TempMarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTempMarketRes extends ConstructorExpression<TempMarketRes> {

    private static final long serialVersionUID = 941253431L;

    public QTempMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Integer> cheerCount, com.querydsl.core.types.Expression<Boolean> isCheer, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(TempMarketRes.class, new Class<?>[]{long.class, String.class, String.class, int.class, boolean.class, java.time.LocalDateTime.class}, marketId, marketName, thumbnail, cheerCount, isCheer, createdAt);
    }

}

