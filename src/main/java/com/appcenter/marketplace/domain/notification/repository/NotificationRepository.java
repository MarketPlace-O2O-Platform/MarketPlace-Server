package com.appcenter.marketplace.domain.notification.repository;

import com.appcenter.marketplace.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
