package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketRes is a Querydsl Projection type for MarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketRes extends ConstructorExpression<MarketRes> {

    private static final long serialVersionUID = 1968757047L;

    public QMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class}, marketId, name, description, address, thumbnail, isFavorite);
    }

}

