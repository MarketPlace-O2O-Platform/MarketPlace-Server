package com.appcenter.marketplace.domain.notification.service;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.notification.Notification;
import com.appcenter.marketplace.domain.notification.TargetType;
import com.appcenter.marketplace.domain.notification.dto.req.NotificationReq;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationPageRes;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;
import com.appcenter.marketplace.domain.notification.repository.NotificationRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.NOTIFICATION_NOT_EXIST;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;


    @Transactional
    @Override
    public NotificationRes createNotification(Long memberId, NotificationReq notificationReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(StatusCode.MEMBER_NOT_EXIST));

        return NotificationRes.from(notificationRepository.save(notificationReq.toEntity(member)));
    }

    @Transactional
    @Override
    public void setNotificationRead(Long notificationId) {
        Notification notification= notificationRepository.findById(notificationId)
                .orElseThrow(()  -> new CustomException(NOTIFICATION_NOT_EXIST));

        notification.setIsReadTrue();
    }

    @Override
    public NotificationPageRes<NotificationRes> getNotificationList(Long memberId, Long notificationId, String type, Integer size) {
        List<NotificationRes> notificationList=new ArrayList<>();

        if(type==null){
            notificationList = notificationRepository.getNotificationList(memberId, notificationId,  size);
        }
        else if(TargetType.exists(type)){
            TargetType targetType = TargetType.valueOf(type.toUpperCase());
            notificationList = notificationRepository.getNotificationListByType(memberId, notificationId, targetType, size);
        }
        else throw new CustomException(NOTIFICATION_NOT_EXIST);


        return checkNextPageAndReturn(notificationList, size);
    }


    private <T> NotificationPageRes<T> checkNextPageAndReturn(List<T> notificationList, Integer size) {
        boolean hasNext = false;

        if(notificationList.size() > size){
            hasNext = true;
            notificationList.remove(size.intValue());
        }

        return new NotificationPageRes<>(notificationList, hasNext);
    }
}
