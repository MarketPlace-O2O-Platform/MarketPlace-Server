package com.appcenter.marketplace.global.fcm;

import com.appcenter.marketplace.domain.favorite.Favorite;
import com.appcenter.marketplace.domain.favorite.repository.FavoriteRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.common.TargetType;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.FcmException;
import com.appcenter.marketplace.global.fcm.event.SendNewCouponFcmEvent;
import com.appcenter.marketplace.global.fcm.event.SubscribeMarketEvent;
import com.appcenter.marketplace.global.fcm.event.UnSubscribeMarketEvent;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final FavoriteRepository favoriteRepository;

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

                firebaseMessaging.send(message);
            }
        }
    }



    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Retryable(
            recover = "recoverSendNewCouponFcmToSubscriber",
            retryFor = FirebaseMessagingException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void sendNewCouponFcmToSubscriber(SendNewCouponFcmEvent event) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle(event.getCoupon().getName())
                                .setBody(event.getCoupon().getDescription())
                                .build()
                )
                .putData("id", String.valueOf(event.getMarket().getId()))   // 대상 ID
                .putData("type", TargetType.MARKET.name())                  // 대상 엔티티 타입
                .setTopic("market-" + event.getMarket().getId())
                .build();

        try {
            String response = firebaseMessaging.send(message);
            log.info("메세지 발송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            MessagingErrorCode errorCode = e.getMessagingErrorCode();
            if (errorCode.equals(MessagingErrorCode.INTERNAL) || errorCode.equals(MessagingErrorCode.UNAVAILABLE)) {
                log.info("FCM 신규 쿠폰 알림 발송 실패");
                throw e;
            }
            else
                throw new FcmException(StatusCode.FCM_SEND_FAIL);

        }
    }

    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Retryable(
            recover = "recoverSubscribeMarket",
            retryFor = FirebaseMessagingException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void subscribeMarket (SubscribeMarketEvent event) throws FirebaseMessagingException{

        try {
            FirebaseMessaging
                    .getInstance()
                    .subscribeToTopic(Collections.singletonList(event.getMember().getFcmToken())
                            , ("market-" + event.getMarket().getId()));
        } catch (FirebaseMessagingException e) {
            MessagingErrorCode errorCode = e.getMessagingErrorCode();
            if (errorCode.equals(MessagingErrorCode.INTERNAL) || errorCode.equals(MessagingErrorCode.UNAVAILABLE)) {
                log.info("FCM 매장 구독 실패");
                throw e;
            }
            else
                throw new FcmException(StatusCode.FCM_SUBSCRIBE_FAIL);
        }
    }

    @Async("FcmExecutor")
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Retryable(
            recover = "recoverUnSubscribeMarket",
            retryFor = FirebaseMessagingException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void unSubscribeMarket(UnSubscribeMarketEvent event) throws FirebaseMessagingException{

        try {
            FirebaseMessaging
                    .getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(event.getMember().getFcmToken())
                            , ("market-" + event.getMarket().getId()));
        } catch (FirebaseMessagingException e) {
            MessagingErrorCode errorCode = e.getMessagingErrorCode();
            if (errorCode.equals(MessagingErrorCode.INTERNAL) || errorCode.equals(MessagingErrorCode.UNAVAILABLE)) {
                log.info("FCM 매장 구독취소 실패");
                throw e;
            }
            else
                throw new FcmException(StatusCode.FCM_UNSUBSCRIBE_FAIL);
        }
    }



    @Recover
    public void recoverSendNewCouponFcmToSubscriber(FirebaseMessagingException e, SendNewCouponFcmEvent event){
        throw new FcmException(StatusCode.FCM_SEND_FAIL);
    }

    @Recover
    @Transactional
    public void recoverSubscribeMarket(FirebaseMessagingException e, SubscribeMarketEvent event){
        Favorite favorite= favoriteRepository.findByMember_IdAndMarket_Id(event.getMember().getId(),event.getMarket().getId()).get();
        favorite.toggleIsDeleted();
        log.info("FCM 매장 구독 실패 -> 매장 찜 철회");
    }

    @Recover
    @Transactional
    public void recoverUnSubscribeMarket(FirebaseMessagingException e, UnSubscribeMarketEvent event){
        Favorite favorite= favoriteRepository.findByMember_IdAndMarket_Id(event.getMember().getId(),event.getMarket().getId()).get();
        favorite.toggleIsDeleted();
        log.info("FCM 매장 구독취소 실패 -> 매장 찜 취소 철회");
    }
}

