package com.appcenter.marketplace.domain.notification.service;

import com.appcenter.marketplace.domain.notification.dto.req.NotificationReq;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;

public interface NotificationService {
    NotificationRes createNotification(Long memberId, NotificationReq notificationReq);

}
