package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

import com.querydsl.core.types.Expression;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketRes is a Querydsl Projection type for MarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketRes extends ConstructorExpression<MarketRes> {

    private static final long serialVersionUID = 1968757047L;

    public QMarketRes(Expression<Long> marketId, Expression<String> marketName, Expression<String> marketDescription, Expression<String> address, Expression<String> thumbnail, Expression<Boolean> isFavorite, Expression<Boolean> isNewCoupon) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon);
    }

    public QMarketRes(Expression<Long> marketId, Expression<String> marketName, Expression<String> marketDescription, Expression<String> address, Expression<String> thumbnail, Expression<Boolean> isFavorite, Expression<Boolean> isNewCoupon, Expression<Long> favoriteCount) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, favoriteCount);
    }

    public QMarketRes(Expression<Long> marketId, Expression<String> marketName, Expression<String> thumbnail, Expression<Boolean> isFavorite) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, boolean.class}, marketId, marketName, thumbnail, isFavorite);
    }

    public QMarketRes(NumberExpression<Long> marketId, StringExpression marketName, StringExpression marketDescription, StringExpression address, StringExpression thumbnail, BooleanExpression isFavorite, BooleanExpression isNewCoupon, TemporalExpression<java.time.LocalDateTime> AtParam) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, AtParam);
    }

}

