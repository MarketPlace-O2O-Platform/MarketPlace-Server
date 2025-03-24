package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_coupon.dto.res.QIssuedCouponRes is a Querydsl Projection type for IssuedCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QIssuedCouponRes extends ConstructorExpression<IssuedCouponRes> {

    private static final long serialVersionUID = 422510221L;

    public QIssuedCouponRes(com.querydsl.core.types.Expression<Long> memberCouponId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadLine, com.querydsl.core.types.Expression<Boolean> used) {
        super(IssuedCouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, java.time.LocalDateTime.class, boolean.class}, memberCouponId, couponId, couponName, description, deadLine, used);
    }

}

