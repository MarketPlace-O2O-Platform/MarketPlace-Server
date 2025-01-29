package com.appcenter.marketplace.global.scheduler;

import com.appcenter.marketplace.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class CheerTicketScheduler {
    private final MemberService memberService;

    @Scheduled(cron = "${schedule.cron.run-daily}", zone= "Asia/Seoul")
    public void reChargeCheerTicket() {
        log.info("CheerTicketScheduler.reChargeCheerTicket: 매일 자정 회원 공감권 1개씩 충전 ");
        try{
            long resetCheerTickets = memberService.resetCheerTickets();
            log.info("CheerTicketScheduler.reChargeCheerTicket: {}명 충전 완료", resetCheerTickets);

        }catch(Exception e){
            log.error("CheerTicketScheduler.reChargeCheerTicket: 에러 발생 - {}", e.getMessage());
        }
    }
}
