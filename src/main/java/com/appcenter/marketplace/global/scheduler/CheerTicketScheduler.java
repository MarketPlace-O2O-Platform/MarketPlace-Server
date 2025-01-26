package com.appcenter.marketplace.global.scheduler;

import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
@RequiredArgsConstructor
public class CheerTicketScheduler {
    private final MemberRepository memberRepository;

    @Scheduled(cron = "${schedule.cheerTicket.cron}", zone= "Asia/Seoul")
    @Transactional
    public void reChargeCheerTicket() {
        log.info("CheerTicketScheduler.reChargeCheerTicket: 매일 자정 회원 공감권 1개씩 충전 ");
        try{
            long resetCheerTickets = memberRepository.resetCheerTickets();
            log.info("CheerTicketScheduler.reChargeCheerTicket: {}명 충전 완료", resetCheerTickets);

        }catch(Exception e){
            log.error("CheerTicketScheduler.reChargeCheerTicket: 에러 발생");
        }
    }
}
