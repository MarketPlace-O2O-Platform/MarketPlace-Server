package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QClosingCouponRes is a Querydsl Projection type for ClosingCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QClosingCouponRes extends ConstructorExpression<ClosingCouponRes> {

    private static final long serialVersionUID = 1262028520L;

    public QClosingCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadline, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail) {
        super(ClosingCouponRes.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, long.class, String.class, String.class}, couponId, couponName, deadline, marketId, marketName, thumbnail);
    }

}

