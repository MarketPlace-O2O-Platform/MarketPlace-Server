package com.appcenter.marketplace.global.fcm.event;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.member.Member;
import lombok.Getter;

@Getter
public class UnSubscribeMarketEvent {
    private final Member member;
    private final Market market;

    public UnSubscribeMarketEvent(Member member, Market market) {
        this.member = member;
        this.market = market;
    }
}
