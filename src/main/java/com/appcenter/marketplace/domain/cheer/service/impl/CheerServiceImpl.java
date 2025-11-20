package com.appcenter.marketplace.domain.cheer.service.impl;

import com.appcenter.marketplace.domain.cheer.Cheer;
import com.appcenter.marketplace.domain.cheer.CheerRepository;
import com.appcenter.marketplace.domain.cheer.dto.CheerCountRes;
import com.appcenter.marketplace.domain.cheer.service.CheerService;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.domain.tempMarket.repository.TempMarketRepository;

import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
public class CheerServiceImpl implements CheerService {
    private final CheerRepository cheerRepository;
    private final MemberRepository memberRepository;
    private final TempMarketRepository tempMarketRepository;

    @Override
    @Transactional
    public CheerCountRes cheerTempMarket(Long memberId, Long marketId) {
        TempMarket tempMarket = tempMarketRepository.findById(marketId).orElseThrow(()-> new CustomException(MARKET_NOT_EXIST));
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new CustomException(MEMBER_NOT_EXIST));

        // 1. 공감권 체크
        if(member.getCheerTicket() == 0){
            throw new CustomException(TICKET_SOLD_OUT);
        }

        // 2. 이미 공감했는지 체크
        Optional<Cheer> isCheer = cheerRepository.findByMemberIdAndTempMarketId(memberId, marketId);
        if(isCheer.isPresent()){
            throw new CustomException(ALREADY_CHEERED);
        }

        // 3. 공감 처리
        Cheer cheer = Cheer.builder()
                .member(member)
                .tempMarket(tempMarket)
                .build();

        cheerRepository.save(cheer);

        tempMarket.increaseCheerCount();
        member.reduceTicket();

        return CheerCountRes.toDto(tempMarket);
    }
}
