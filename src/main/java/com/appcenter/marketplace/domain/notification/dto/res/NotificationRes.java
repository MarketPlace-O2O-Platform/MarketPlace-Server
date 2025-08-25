package com.appcenter.marketplace.domain.notification.dto.res;

import com.appcenter.marketplace.domain.notification.Notification;
import com.appcenter.marketplace.domain.notification.TargetType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class NotificationRes {

    private final Long id;
    private final String title;
    private final String body;
    private final Long targetId;
    private final TargetType targetType;
    private final Boolean isRead;
    private final LocalDateTime createdAt;

    @QueryProjection
    @Builder
    public NotificationRes(Long id, String title, String body, Long targetId, TargetType targetType, Boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.targetId = targetId;
        this.targetType = targetType;
        this.isRead= isRead;
        this.createdAt = createdAt;
    }

    public static NotificationRes from(Notification notification) {
        return NotificationRes.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .body(notification.getBody())
                .targetId(notification.getTargetId())
                .targetType(notification.getTargetType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
