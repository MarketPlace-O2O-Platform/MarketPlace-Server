package com.appcenter.marketplace;


import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.favorite.repository.FavoriteRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.config.RetryConfig;
import com.appcenter.marketplace.global.exception.FcmException;
import com.appcenter.marketplace.global.fcm.FcmService;
import com.appcenter.marketplace.global.fcm.event.SendNewCouponFcmEvent;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FcmService.class, RetryConfig.class}) // 필요한 Bean만 로드
public class FcmRetryTest {
    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private FavoriteRepository favoriteRepository;

    @MockBean
    private SendNewCouponFcmEvent sendNewCouponFcmEvent;

    @MockBean
    private FirebaseMessagingException firebaseMessagingException;

    @Autowired
    private FcmService fcmService;

    @MockBean
    private Market market; // Market도 Mock

    @MockBean
    private Coupon coupon; // Coupon도 Mock



    @Test
    @DisplayName("FCM 서버 에러 시 최대 3번 재시도 후 Recover 메서드 실행")
    void testRetry() throws FirebaseMessagingException {
        // given
        given(firebaseMessagingException.getMessagingErrorCode()).willReturn(MessagingErrorCode.INTERNAL);
        given(firebaseMessaging.send(any(Message.class))).willThrow(firebaseMessagingException);

        when(sendNewCouponFcmEvent.getCoupon()).thenReturn(coupon);
        when(coupon.getName()).thenReturn("New Coupon");
        when(coupon.getDescription()).thenReturn("Special Discount Coupon");

        when(sendNewCouponFcmEvent.getMarket()).thenReturn(market);
        when(market.getId()).thenReturn(1L);

//        fcmService.sendNewCouponFcmToSubscriber(sendNewCouponFcmEvent);
        // when, then
        assertThatThrownBy(() ->
                fcmService.sendNewCouponFcmToSubscriber(sendNewCouponFcmEvent))
                .isInstanceOf(FcmException.class);

        // then
        // FirebaseMessaging.send() 메서드가 3번 호출되었는지 검증
        verify(firebaseMessaging, times(3)).send(any(Message.class));

    }

    @Test
    @DisplayName("재시도 중 정상 응답이 오면 재시도하지 않는다")
    void testRetryWithSuccessfulResponse() throws FirebaseMessagingException {
        // given

        given(firebaseMessagingException.getMessagingErrorCode()).willReturn(MessagingErrorCode.INTERNAL);
        // 첫 번째 호출에서는 예외를 던짐
        given(firebaseMessaging.send(any(Message.class))).willThrow(firebaseMessagingException)
                // 두 번째 호출에서는 정상 응답을 반환
                .willReturn("Success response");

        when(sendNewCouponFcmEvent.getCoupon()).thenReturn(coupon);
        when(coupon.getName()).thenReturn("New Coupon");
        when(coupon.getDescription()).thenReturn("Special Discount Coupon");

        when(sendNewCouponFcmEvent.getMarket()).thenReturn(market);
        when(market.getId()).thenReturn(1L);

        // when
        fcmService.sendNewCouponFcmToSubscriber(sendNewCouponFcmEvent);

        // then
        // 정상 응답이 들어오면 재시도가 멈추어야 하므로 총 두 번 호출됨
        verify(firebaseMessaging, times(2)).send(any(Message.class));
    }
}
