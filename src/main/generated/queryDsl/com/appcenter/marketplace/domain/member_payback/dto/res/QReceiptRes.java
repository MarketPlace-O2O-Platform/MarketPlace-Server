package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_payback.dto.res.QReceiptRes is a Querydsl Projection type for ReceiptRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReceiptRes extends ConstructorExpression<ReceiptRes> {

    private static final long serialVersionUID = 1543790023L;

    public QReceiptRes(com.querydsl.core.types.Expression<Long> memberCouponId, com.querydsl.core.types.Expression<String> receipt, com.querydsl.core.types.Expression<String> account, com.querydsl.core.types.Expression<String> accountNumber, com.querydsl.core.types.Expression<Boolean> isUsed) {
        super(ReceiptRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, boolean.class}, memberCouponId, receipt, account, accountNumber, isUsed);
    }

}

