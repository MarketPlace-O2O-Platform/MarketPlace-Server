package com.appcenter.marketplace.domain.notification.repository;

import com.appcenter.marketplace.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification,Long> , NotificationRepositoryCustom{

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.member.id = :memberId AND n.isRead = false")
    void setAllNotificationAsReadByMemberId(@Param("memberId") Long memberId);

}
