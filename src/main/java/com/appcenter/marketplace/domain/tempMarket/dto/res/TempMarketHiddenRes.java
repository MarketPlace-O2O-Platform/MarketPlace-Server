package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TempMarketHiddenRes {
    private final Long marketId;
    private final Boolean isHidden;

    @Builder
    private TempMarketHiddenRes(Long marketId, Boolean isHidden) {
        this.marketId = marketId;
        this.isHidden = isHidden;
    }

    public static TempMarketHiddenRes toDto(TempMarket market) {
        return TempMarketHiddenRes.builder()
                .marketId(market.getId())
                .isHidden(market.getIsHidden())
                .build();
    }
}
