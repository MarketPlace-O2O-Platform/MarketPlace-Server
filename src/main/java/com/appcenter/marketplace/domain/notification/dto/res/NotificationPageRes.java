package com.appcenter.marketplace.domain.notification.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class NotificationPageRes<T> {
    private final List<T> notificationResList;
    private final boolean hasNext;

    public NotificationPageRes(List<T> notificationResList, boolean hasNext){
        this.notificationResList = notificationResList;
        this.hasNext = hasNext;
    }
}
