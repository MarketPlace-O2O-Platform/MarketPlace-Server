package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QCouponMemberRes is a Querydsl Projection type for CouponMemberRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponMemberRes extends ConstructorExpression<CouponMemberRes> {

    private static final long serialVersionUID = 706131005L;

    public QCouponMemberRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadLine) {
        super(CouponMemberRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class}, couponId, couponName, description, deadLine);
    }

}

