package com.appcenter.marketplace.domain.payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

import com.querydsl.core.types.Expression;

/**
 * com.appcenter.marketplace.domain.payback.dto.res.QPaybackRes is a Querydsl Projection type for PaybackRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPaybackRes extends ConstructorExpression<PaybackRes> {

    private static final long serialVersionUID = -1196103089L;

    public QPaybackRes(Expression<Long> couponId, Expression<String> couponName, Expression<String> couponDescription, Expression<Boolean> isHidden, Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(PaybackRes.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, couponId, couponName, couponDescription, isHidden, couponType);
    }

    public QPaybackRes(Expression<Long> couponId, Expression<String> couponName, Expression<String> couponDescription, Expression<Boolean> isHidden, Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType, Expression<Long> marketId, Expression<String> marketName) {
        super(PaybackRes.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class, long.class, String.class}, couponId, couponName, couponDescription, isHidden, couponType, marketId, marketName);
    }

    public QPaybackRes(NumberExpression<Long> couponId, StringExpression couponName, StringExpression couponDescription, EnumExpression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType, BooleanExpression isMemberIssued) {
        super(PaybackRes.class, new Class<?>[]{long.class, String.class, String.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class, boolean.class}, couponId, couponName, couponDescription, couponType, isMemberIssued);
    }

}

