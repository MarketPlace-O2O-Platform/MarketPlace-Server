package com.appcenter.marketplace.domain.notification.repository;

import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;

import java.util.List;

public interface NotificationRepositoryCustom {
    List<NotificationRes> getNotificationList(Long memberId, Long notificationId, Integer size);
}
