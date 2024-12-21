package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QLatestCouponRes is a Querydsl Projection type for LatestCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLatestCouponRes extends ConstructorExpression<LatestCouponRes> {

    private static final long serialVersionUID = -1601221882L;

    public QLatestCouponRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> marketDescription, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(LatestCouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, boolean.class, java.time.LocalDateTime.class}, marketId, couponId, marketName, marketDescription, address, thumbnail, isFavorite, modifiedAt);
    }

}

