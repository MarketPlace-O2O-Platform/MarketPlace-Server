package com.appcenter.marketplace.global.fcm;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import com.appcenter.marketplace.global.fcm.event.SendNewCouponFcmEvent;
import com.appcenter.marketplace.global.fcm.event.SubscribeMarketEvent;
import com.appcenter.marketplace.global.fcm.event.UnSubscribeMarketEvent;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

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

    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendNewCouponFcmToSubscriber(SendNewCouponFcmEvent event){
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(event.getCoupon().getName())
                                .setBody(event.getCoupon().getDescription())
                                .build()
                )
                .setTopic("market-"+ event.getMarket().getId())
                .build();

        try{
            String response= firebaseMessaging.send(message);
            log.info("메세지 발송 성공: {}", response);
        } catch (FirebaseMessagingException e){
            log.error("예외 발생: {}", e.getErrorCode());
            throw new CustomException(StatusCode.FCM_SEND_FAIL);
        }

    }

    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void subscribeMarket(SubscribeMarketEvent event){

        try {
            FirebaseMessaging
                    .getInstance()
                    .subscribeToTopic(Collections.singletonList(event.getMember().getFcmToken())
                            , ("market-" + event.getMarket().getId().toString()));
        } catch (FirebaseMessagingException e){
            throw new CustomException(StatusCode.FCM_SUBSCRIBE_FAIL);
        }

//        ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
//                .getInstance()
//                .subscribeToTopicAsync(Collections.singletonList(event.getMember().getFcmToken())
//                        ,("market-"+ event.getMarket().getId().toString()));
//
//        apiFuture.addListener(() ->{
//            try{
//                TopicManagementResponse response = apiFuture.get();
//                log.info("토픽 구독 성공: {}", response.getSuccessCount());
//            } catch (ExecutionException | InterruptedException e) {
//                log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
//                throw new CustomException(StatusCode.FCM_SUBSCRIBE_FAIL);
//            }
//        }, asyncConfig.getFcmExecutor());
    }

    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void unsubscribeMarket(UnSubscribeMarketEvent event) {

        try {
            FirebaseMessaging
                    .getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(event.getMember().getFcmToken())
                            , ("market-" + event.getMarket().getId().toString()));
        } catch (FirebaseMessagingException e){
            throw new CustomException(StatusCode.FCM_UNSUBSCRIBE_FAIL);
        }

//        ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
//                .getInstance()
//                .subscribeToTopicAsync(Collections.singletonList(event.getMember().getFcmToken())
//                        ,("market-"+ event.getMarket().getId().toString()));
//
//
//        apiFuture.addListener(() ->{
//            try{
//                TopicManagementResponse response = apiFuture.get();
//                log.info("토픽 구독취소 성공: {}", response.getSuccessCount());
//            } catch (ExecutionException | InterruptedException e) {
//                log.error("토픽 구독취소 관련 예외 발생: {}", e.getMessage());
//                throw new CustomException(StatusCode.FCM_UNSUBSCRIBE_FAIL);
//            }
//        }, asyncConfig.getFcmExecutor());
    }


}
