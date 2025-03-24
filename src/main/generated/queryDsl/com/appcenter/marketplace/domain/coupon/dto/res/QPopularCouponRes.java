package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QPopularCouponRes is a Querydsl Projection type for PopularCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPopularCouponRes extends ConstructorExpression<PopularCouponRes> {

    private static final long serialVersionUID = -1833494204L;

    public QPopularCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isAvailable, com.querydsl.core.types.Expression<Boolean> isMemberIssued, com.querydsl.core.types.Expression<Long> issuedCount) {
        super(PopularCouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class}, couponId, couponName, marketId, marketName, address, thumbnail, isAvailable, isMemberIssued, issuedCount);
    }

}

