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

    public QCouponRes(Expression<Long> couponId, Expression<String> couponName, Expression<String> couponDescription, Expression<java.time.LocalDateTime> deadLine, Expression<Integer> stock, Expression<Boolean> isHidden, Expression<Long> marketId, Expression<String> marketName) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, int.class, boolean.class, long.class, String.class}, couponId, couponName, couponDescription, deadLine, stock, isHidden, marketId, marketName);
    }

    public QCouponRes(Expression<Long> couponId, Expression<String> couponName, Expression<String> couponDescription, Expression<java.time.LocalDateTime> deadLine, Expression<Boolean> isAvailable, Expression<Boolean> isMemberIssued, Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, boolean.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, couponId, couponName, couponDescription, deadLine, isAvailable, isMemberIssued, couponType);
    }

    public QCouponRes(Expression<Long> couponId, Expression<String> couponName, Expression<Long> marketId, Expression<String> marketName, Expression<String> address, Expression<String> thumbnail, Expression<Boolean> isAvailable, Expression<Boolean> isMemberIssued, Expression<java.time.LocalDateTime> couponCreatedAt) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class}, couponId, couponName, marketId, marketName, address, thumbnail, isAvailable, isMemberIssued, couponCreatedAt);
    }

    public QCouponRes(NumberExpression<Long> couponId, StringExpression couponName, NumberExpression<Long> marketId, StringExpression marketName, StringExpression address, StringExpression thumbnail, BooleanExpression isAvailable, BooleanExpression isMemberIssued, NumberExpression<Long> issuedCount) {
        super(CouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, String.class, boolean.class, boolean.class, long.class}, couponId, couponName, marketId, marketName, address, thumbnail, isAvailable, isMemberIssued, issuedCount);
    }

}

