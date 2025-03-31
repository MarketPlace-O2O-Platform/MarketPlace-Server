package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

import com.querydsl.core.types.Expression;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QCouponRes is a Querydsl Projection type for CouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponRes extends ConstructorExpression<CouponRes> {

    private static final long serialVersionUID = 1145762423L;

    public QCouponRes(Expression<Long> couponId, Expression<String> couponName, Expression<String> couponDescription, Expression<java.time.LocalDateTime> deadLine, Expression<Integer> stock, Expression<Boolean> isHidden) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, int.class, boolean.class}, couponId, couponName, couponDescription, deadLine, stock, isHidden);
    }

    public QCouponRes(NumberExpression<Long> couponId, StringExpression couponName, StringExpression couponDescription, TemporalExpression<java.time.LocalDateTime> deadLine, BooleanExpression isAvailable, BooleanExpression isMemberIssued) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, boolean.class, boolean.class}, couponId, couponName, couponDescription, deadLine, isAvailable, isMemberIssued);
    }

}

