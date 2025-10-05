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

    public QMarketRes(Expression<Long> marketId, Expression<String> marketName, Expression<String> marketDescription, Expression<String> address, Expression<String> thumbnail, Expression<Boolean> isFavorite, Expression<Boolean> isNewCoupon, Expression<com.appcenter.marketplace.global.common.Major> major, Expression<Integer> orderNo) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, com.appcenter.marketplace.global.common.Major.class, int.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, major, orderNo);
    }

    public QMarketRes(NumberExpression<Long> marketId, StringExpression marketName, StringExpression marketDescription, StringExpression address, StringExpression thumbnail, BooleanExpression isFavorite, BooleanExpression isNewCoupon, TemporalExpression<java.time.LocalDateTime> favoriteModifiedAt, EnumExpression<com.appcenter.marketplace.global.common.Major> major) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class, com.appcenter.marketplace.global.common.Major.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, favoriteModifiedAt, major);
    }

}

