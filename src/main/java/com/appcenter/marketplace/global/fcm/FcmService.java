package com.appcenter.marketplace.global.fcm;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

    public void sendFcmMessage(FcmRequest fcmRequest) throws FirebaseMessagingException {
        Optional<Member> member= memberRepository.findById(fcmRequest.memberId);

        if(member.isPresent()){
            if(member.get().getFcmToken() != null){
                Notification notification= Notification.builder()
                        .setTitle(fcmRequest.getTitle())
                        .setBody(fcmRequest.getBody())
                        .build();

                Message message= Message.builder()
                        .setToken(member.get().getFcmToken())
                        .setNotification(notification)
                        .build();

                // 비동기 메시지 전송
                firebaseMessaging.send(message);
            }
        }
    }


}
