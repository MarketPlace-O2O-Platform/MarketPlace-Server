package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QLatestCouponRes is a Querydsl Projection type for LatestCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLatestCouponRes extends ConstructorExpression<LatestCouponRes> {

    private static final long serialVersionUID = -963448592L;

    public QLatestCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isAvailable, com.querydsl.core.types.Expression<Boolean> isMemberIssued, com.querydsl.core.types.Expression<java.time.LocalDateTime> couponCreatedAt) {
        super(LatestCouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class}, couponId, couponName, marketId, marketName, address, thumbnail, isAvailable, isMemberIssued, couponCreatedAt);
    }

    public QLatestCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<java.time.LocalDateTime> couponCreatedAt) {
        super(LatestCouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, java.time.LocalDateTime.class}, couponId, couponName, marketId, marketName, thumbnail, couponCreatedAt);
    }

}

