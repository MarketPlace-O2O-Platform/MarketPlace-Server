package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_payback.dto.res.QReceiptItemRes is a Querydsl Projection type for ReceiptItemRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReceiptItemRes extends ConstructorExpression<ReceiptItemRes> {

    private static final long serialVersionUID = 544227892L;

    public QReceiptItemRes(com.querydsl.core.types.Expression<Long> memberPaybackId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<java.time.LocalDateTime> issuedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> receiptSubmittedAt, com.querydsl.core.types.Expression<String> receipt, com.querydsl.core.types.Expression<Boolean> isPayback) {
        super(ReceiptItemRes.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, boolean.class}, memberPaybackId, couponName, issuedAt, receiptSubmittedAt, receipt, isPayback);
    }

}

