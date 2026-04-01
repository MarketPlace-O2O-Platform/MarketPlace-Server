package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_payback.dto.res.QTopMemberReceiptRes is a Querydsl Projection type for TopMemberReceiptRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopMemberReceiptRes extends ConstructorExpression<TopMemberReceiptRes> {

    private static final long serialVersionUID = -852217512L;

    public QTopMemberReceiptRes(com.querydsl.core.types.Expression<Long> memberId, com.querydsl.core.types.Expression<Long> receiptCount, com.querydsl.core.types.Expression<Long> completedCount, com.querydsl.core.types.Expression<Long> pendingCount) {
        super(TopMemberReceiptRes.class, new Class<?>[]{long.class, long.class, long.class, long.class}, memberId, receiptCount, completedCount, pendingCount);
    }

}

