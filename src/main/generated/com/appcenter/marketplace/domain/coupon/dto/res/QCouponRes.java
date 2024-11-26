package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QCouponRes is a Querydsl Projection type for CouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponRes extends ConstructorExpression<CouponRes> {

    private static final long serialVersionUID = 1145762423L;

    public QCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadLine, com.querydsl.core.types.Expression<Integer> stock, com.querydsl.core.types.Expression<Boolean> isHidden) {
        super(CouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, java.time.LocalDateTime.class, int.class, boolean.class}, couponId, marketId, couponName, description, deadLine, stock, isHidden);
    }

}

