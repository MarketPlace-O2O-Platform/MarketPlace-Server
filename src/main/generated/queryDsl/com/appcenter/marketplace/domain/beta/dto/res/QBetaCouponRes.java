package com.appcenter.marketplace.domain.beta.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.beta.dto.res.QBetaCouponRes is a Querydsl Projection type for BetaCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBetaCouponRes extends ConstructorExpression<BetaCouponRes> {

    private static final long serialVersionUID = -402551119L;

    public QBetaCouponRes(com.querydsl.core.types.Expression<Long> betaCouponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> couponDetail, com.querydsl.core.types.Expression<String> image, com.querydsl.core.types.Expression<Boolean> isUsed, com.querydsl.core.types.Expression<Boolean> isPromise) {
        super(BetaCouponRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class}, betaCouponId, marketName, couponName, couponDetail, image, isUsed, isPromise);
    }

}

