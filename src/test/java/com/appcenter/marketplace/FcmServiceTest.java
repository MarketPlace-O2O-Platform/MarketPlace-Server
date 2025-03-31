package com.appcenter.marketplace;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.coupon.service.impl.CouponOwnerServiceImpl;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.global.fcm.event.SendNewCouponFcmEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FcmServiceTest {

    @InjectMocks
    private CouponOwnerServiceImpl couponOwnerService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private Market market; // Market도 Mock

    @Mock
    private Coupon coupon; // Coupon도 Mock

    @Test
    @DisplayName("트랜잭션이 성공적으로 끝나면 이벤트가 발행된다")
    void testEventPublishedAfterCommit() {
        // Given
        CouponReq couponReq = mock(CouponReq.class);

        when(marketRepository.findById(anyLong())).thenReturn(Optional.of(market));
        when(couponReq.ofCreate(market)).thenReturn(coupon); // Coupon 객체 생성도 Mock 처리
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        // When
        couponOwnerService.createCoupon(couponReq, 1L);

        // Then
        verify(eventPublisher, times(1)).publishEvent(any(SendNewCouponFcmEvent.class));
    }

    @Test
    @DisplayName("예외 발생으로 트랜잭션 롤백 시 이벤트가 발행되지 않는다")
    void testEventNotPublishedOnRollback() {
        // Given
        CouponReq couponReq = mock(CouponReq.class);

        when(marketRepository.findById(anyLong())).thenReturn(Optional.of(market));
        when(couponReq.ofCreate(market)).thenReturn(coupon);
        when(couponRepository.save(any(Coupon.class))).thenThrow(new RuntimeException("DB Error")); // 예외 발생

        // When / Then
        assertThrows(RuntimeException.class, () -> couponOwnerService.createCoupon(couponReq, 1L));

        // 이벤트가 발행되지 않았는지 검증
        verify(eventPublisher, never()).publishEvent(any(SendNewCouponFcmEvent.class));
    }
}
