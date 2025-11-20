package com.appcenter.marketplace.domain.tempMarket.dto.res;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.global.common.Major;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TempMarketDetailRes {
    private final Long marketId;
    private final String marketName;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Integer cheerCount;
    private final Boolean isHidden;
    private final Major category;

    @Builder
    private TempMarketDetailRes(Long marketId, String marketName, String description, String address, String thumbnail, Integer cheerCount, Boolean isHidden, Major  category) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.cheerCount = cheerCount;
        this.isHidden = isHidden;
        this.category = category;
    }

    public static TempMarketDetailRes toDto(TempMarket tempMarket) {
        return TempMarketDetailRes.builder()
                .marketId(tempMarket.getId())
                .marketName(tempMarket.getName())
                .description(tempMarket.getDescription())
                .address(tempMarket.getAddress())
                .thumbnail(tempMarket.getThumbnail())
                .cheerCount(tempMarket.getCheerCount())
                .isHidden(tempMarket.getIsHidden())
                .category(tempMarket.getCategory().getMajor())
                .build();
    }
}
