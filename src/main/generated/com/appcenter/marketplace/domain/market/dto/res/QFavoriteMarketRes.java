package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QFavoriteMarketRes is a Querydsl Projection type for FavoriteMarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFavoriteMarketRes extends ConstructorExpression<FavoriteMarketRes> {

    private static final long serialVersionUID = 1741954043L;

    public QFavoriteMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<Boolean> isNewCoupon, com.querydsl.core.types.Expression<Long> favoriteCount) {
        super(FavoriteMarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class}, marketId, name, description, address, thumbnail, isFavorite, isNewCoupon, favoriteCount);
    }

}

