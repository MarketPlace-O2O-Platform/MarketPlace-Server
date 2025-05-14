package com.appcenter.marketplace.coupon;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReq;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_coupon.service.impl.MemberCouponServiceImpl;
import com.appcenter.marketplace.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;
import static com.appcenter.marketplace.global.common.StatusCode.MEMBER_NOT_EXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@SpringBootTest
//@ContextConfiguration(classes = {MemberCouponServiceImpl.class, CouponRepository.class, MemberRepository.class, MysqlConfig.class}) // 필요한 Bean만 로드
public class CouponConcurrentTest {

    @Autowired
    private MemberCouponServiceImpl memberCouponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    private Long couponId;

    private Long memberId;

    @BeforeEach
    void setUp() {
        Market market = marketRepository.findById(1L).orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
        Member member = memberRepository.findById(201901658L).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        memberId = member.getId();
        CouponReq couponReq = new CouponReq(
                "WELCOME10",                     // couponName
                "10% 할인 쿠폰입니다",            // description
                LocalDateTime.now().plusDays(30), // deadLine
                500                        // stock
        );
        Coupon coupon = couponRepository.save(couponReq.ofCreate(market));
        couponId = coupon.getId();


    }

//    @AfterEach
//    public void tearDown() {
//        memberCouponRepository.deleteAll();
//        couponRepository.deleteById(couponId);
//    }

    @Test
    @DisplayName("쿠폰 다운로드 정합성 테스트")
    void testConcurrentReservation() throws InterruptedException {
        int numberOfThreads = 1000; // 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        List<Exception> exceptions = new ArrayList<>();
        List<Long> responseTimes = new ArrayList<>();

        // 테스트 시작 시간 기록
        long testStartTime = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                long startTime = System.nanoTime(); // 시작 시간 기록
                try {
                    memberCouponService.issuedCoupon(memberId, couponId);
                    successCount.incrementAndGet();
                } catch (PessimisticLockingFailureException e) {
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                } catch (CustomException e) {
                    synchronized (exceptions) {
                        // 다른 예외도 처리
                        exceptions.add(e);
                    }
                } catch (Exception e) {
                    synchronized (exceptions) {
                        // 다른 예외도 처리
                        exceptions.add(e);
                    }
                } finally {
                    long endTime = System.nanoTime(); // 종료 시간 기록
                    long responseTime = endTime - startTime; // 응답 시간 계산
                    synchronized (responseTimes) {
                        responseTimes.add(responseTime); // 응답 시간 리스트에 추가
                    }
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // 테스트 종료 시간 기록
        long testEndTime = System.nanoTime();

        // 전체 테스트 시간 계산 (단위: 밀리초)
        long totalTestTimeMillis = (testEndTime - testStartTime) / 1_000_000; // nano -> milliseconds


        System.out.println("성공한 다운로드 수: " + successCount.get());
        System.out.println("발생한 예외 수: " + exceptions.size());

        // 응답 시간 계산
        List<Long> responseTimeList = new ArrayList<>(responseTimes);
        long fastestResponse = responseTimeList.stream().min(Long::compare).orElse(0L) / 1_000_000; // nanoseconds -> milliseconds
        long slowestResponse = responseTimeList.stream().max(Long::compare).orElse(0L) / 1_000_000; // nanoseconds -> milliseconds
        double averageResponse = responseTimeList.stream().mapToLong(Long::longValue).average().orElse(0) / 1_000_000.0; // nanoseconds -> milliseconds


        exceptions.forEach(ex -> System.out.println("예외 종류: " + ex.getClass().getSimpleName()));

        System.out.println("최단 응답 시간: " + fastestResponse + "ms");
        System.out.println("최장 응답 시간: " + slowestResponse + "ms");
        System.out.println("평균 응답 시간: " + averageResponse + "ms");
        // 전체 테스트 시간 출력
        System.out.println("전체 테스트 시간: " + totalTestTimeMillis + "ms");

        assertEquals(1, successCount.get(), "동시에 하나만 성공해야 합니다");
        assertEquals(numberOfThreads - 1, exceptions.size(), "나머지는 예외가 발생해야 합니다");


    }

}

