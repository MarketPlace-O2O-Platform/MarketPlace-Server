package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TempMarketRes {
    private final Long marketId;
    private final String marketName;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Long cheerCount;
    private final Boolean isHidden;

    @Builder
    private TempMarketRes(Long marketId, String marketName, String description, String address, String thumbnail, Long cheerCount, Boolean isHidden) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.cheerCount = cheerCount;
        this.isHidden = isHidden;
    }

    public static TempMarketRes toDto(TempMarket tempMarket) {
        return TempMarketRes.builder()
                .marketId(tempMarket.getId())
                .marketName(tempMarket.getName())
                .description(tempMarket.getDescription())
                .address(tempMarket.getAddress())
                .thumbnail(tempMarket.getThumbnail())
                .cheerCount(tempMarket.getCheerCount())
                .isHidden(tempMarket.getIsHidden())
                .build();
    }
}
