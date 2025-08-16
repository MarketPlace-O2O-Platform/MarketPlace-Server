package com.appcenter.marketplace.domain.notification.repository;

import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;
import com.appcenter.marketplace.domain.notification.dto.res.QNotificationRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.appcenter.marketplace.domain.member.QMember.member;
import static com.appcenter.marketplace.domain.notification.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<NotificationRes> getNotificationList(Long memberId, Long notificationId, Integer size) {
        return jpaQueryFactory.select(new QNotificationRes(
                notification.id,
                notification.title,
                notification.body,
                notification.targetId,
                notification.targetType,
                notification.isRead))
                .from(notification)
                .join(member).on(member.id.eq(memberId))
                .where(ltNotificationId(notificationId))
                .orderBy(notification.id.desc())
                .limit(size + 1)
                .fetch();
    }

    private BooleanBuilder ltNotificationId(Long notificationId) {
        BooleanBuilder builder = new BooleanBuilder();
        if( notificationId !=  null){
            builder.and(notification.id.lt(notificationId));
        }
        return builder;
    }
}
