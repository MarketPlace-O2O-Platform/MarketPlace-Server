package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QCouponMemberResDto is a Querydsl Projection type for CouponMemberResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponMemberResDto extends ConstructorExpression<CouponMemberRes> {

    private static final long serialVersionUID = -400976798L;

    public QCouponMemberResDto(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadLine) {
        super(CouponMemberRes.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class}, couponId, couponName, description, deadLine);
    }

}

