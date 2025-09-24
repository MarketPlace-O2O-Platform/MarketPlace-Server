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

    public QMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> marketDescription, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<Boolean> isNewCoupon, com.querydsl.core.types.Expression<com.appcenter.marketplace.global.common.Major> major) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, com.appcenter.marketplace.global.common.Major.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, major);
    }

    public QMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> marketDescription, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<Boolean> isNewCoupon, com.querydsl.core.types.Expression<java.time.LocalDateTime> favoriteModifiedAt, com.querydsl.core.types.Expression<com.appcenter.marketplace.global.common.Major> major) {
        super(MarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class, com.appcenter.marketplace.global.common.Major.class}, marketId, marketName, marketDescription, address, thumbnail, isFavorite, isNewCoupon, favoriteModifiedAt, major);
    }

}

