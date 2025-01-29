package com.appcenter.marketplace.global.scheduler;

import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberCouponExpireScheduler {
    private final MemberCouponService memberCouponService;

    // 받은 쿠폰이 3일뒤 사용 안했다면 만료
    @Scheduled(cron = "${schedule.cron.run-daily}", zone="Asia/Seoul")
    public void check3DaysCoupons() {
        try{
            memberCouponService.check3DaysCoupons();
        }catch(Exception e){
            log.error("MemberCouponExpireScheduler: {} 에러 발생", e.getMessage());
        }
    }

    // 쿠폰의 deadLine이 넘어가면 만료
    @Scheduled(cron = "${schedule.cron.run-daily}", zone = "Asia/Seoul")
    public void checkExpireCoupons() {
        try{
            memberCouponService.checkExpiredCoupons();
        }catch(Exception e){
            log.error("MemberCouponExpireScheduler.checkExpireCoupons: {} 에러 발생", e.getMessage());
        }
    }
}
