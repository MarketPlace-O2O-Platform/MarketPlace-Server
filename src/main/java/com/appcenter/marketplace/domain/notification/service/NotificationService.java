package com.appcenter.marketplace.domain.notification.service;

import com.appcenter.marketplace.domain.notification.dto.req.NotificationReq;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationPageRes;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;

public interface NotificationService {
    NotificationRes createNotification(Long memberId, NotificationReq notificationReq);

    NotificationPageRes<NotificationRes> getNotificationList(Long memberId, Long notificationId, Integer size);

    void setNotificationRead(Long notificationId);
}
