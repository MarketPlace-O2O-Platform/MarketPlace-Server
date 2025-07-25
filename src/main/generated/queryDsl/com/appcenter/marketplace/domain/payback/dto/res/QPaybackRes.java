package com.appcenter.marketplace.domain.payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.payback.dto.res.QPaybackRes is a Querydsl Projection type for PaybackRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPaybackRes extends ConstructorExpression<PaybackRes> {

    private static final long serialVersionUID = -1196103089L;

    public QPaybackRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> couponDescription, com.querydsl.core.types.Expression<Boolean> isHidden, com.querydsl.core.types.Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(PaybackRes.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, couponId, couponName, couponDescription, isHidden, couponType);
    }

}

