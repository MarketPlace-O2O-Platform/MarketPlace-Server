package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QTopFavoriteMarketRes is a Querydsl Projection type for TopFavoriteMarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopFavoriteMarketRes extends ConstructorExpression<TopFavoriteMarketRes> {

    private static final long serialVersionUID = -1900564640L;

    public QTopFavoriteMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail) {
        super(TopFavoriteMarketRes.class, new Class<?>[]{long.class, String.class, String.class}, marketId, marketName, thumbnail);
    }

}

