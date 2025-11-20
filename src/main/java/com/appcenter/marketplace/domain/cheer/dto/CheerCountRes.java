package com.appcenter.marketplace.domain.cheer.dto;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CheerCountRes {
    private final Integer cheerCount;

    @Builder
    private CheerCountRes(Integer cheerCount) {
        this.cheerCount = cheerCount;
    }

    public static CheerCountRes toDto(TempMarket tempMarket) {
        return CheerCountRes.builder()
                .cheerCount(tempMarket.getCheerCount())
                .build();
    }
}
