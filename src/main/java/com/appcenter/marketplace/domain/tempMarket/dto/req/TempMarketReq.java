package com.appcenter.marketplace.domain.tempMarket.dto.req;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TempMarketReq {

    private String category;
    private String marketName;

    private String description;

    private String address;

    @Builder
    public TempMarketReq(String marketName, String description, String address) {
        this.marketName = marketName;
        this.description = description;
        this.address = address;
    }

    public TempMarket toEntity(Category category, String imageFile) {
        return TempMarket.builder()
                .category(category)
                .name(marketName)
                .description(description)
                .address(address)
                .thumbnail(imageFile)
                .cheerCount(0L)
                .isHidden(false)
                .build();
    }
}
