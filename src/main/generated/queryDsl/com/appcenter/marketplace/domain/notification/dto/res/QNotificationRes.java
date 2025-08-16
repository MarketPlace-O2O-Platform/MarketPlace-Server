package com.appcenter.marketplace.domain.notification.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.notification.dto.res.QNotificationRes is a Querydsl Projection type for NotificationRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QNotificationRes extends ConstructorExpression<NotificationRes> {

    private static final long serialVersionUID = -905529833L;

    public QNotificationRes(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> body, com.querydsl.core.types.Expression<Long> targetId, com.querydsl.core.types.Expression<com.appcenter.marketplace.global.common.TargetType> targetType, com.querydsl.core.types.Expression<Boolean> isRead) {
        super(NotificationRes.class, new Class<?>[]{long.class, String.class, String.class, long.class, com.appcenter.marketplace.global.common.TargetType.class, boolean.class}, id, title, body, targetId, targetType, isRead);
    }

}

