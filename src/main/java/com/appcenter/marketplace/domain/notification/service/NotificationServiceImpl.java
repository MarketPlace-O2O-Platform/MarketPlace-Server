package com.appcenter.marketplace.domain.notification.service;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.notification.dto.req.NotificationReq;
import com.appcenter.marketplace.domain.notification.dto.res.NotificationRes;
import com.appcenter.marketplace.domain.notification.repository.NotificationRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
