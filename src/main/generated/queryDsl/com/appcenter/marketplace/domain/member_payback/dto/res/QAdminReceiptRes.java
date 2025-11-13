package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_payback.dto.res.QAdminReceiptRes is a Querydsl Projection type for AdminReceiptRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminReceiptRes extends ConstructorExpression<AdminReceiptRes> {

    private static final long serialVersionUID = -1772836648L;

    public QAdminReceiptRes(com.querydsl.core.types.Expression<Long> memberPaybackId, com.querydsl.core.types.Expression<Long> memberId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<java.time.LocalDateTime> issuedAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> receiptSubmittedAt, com.querydsl.core.types.Expression<String> receipt, com.querydsl.core.types.Expression<Boolean> isPayback, com.querydsl.core.types.Expression<String> account, com.querydsl.core.types.Expression<String> accountNumber) {
        super(AdminReceiptRes.class, new Class<?>[]{long.class, long.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, boolean.class, String.class, String.class}, memberPaybackId, memberId, couponName, issuedAt, receiptSubmittedAt, receipt, isPayback, account, accountNumber);
    }

}

