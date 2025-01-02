package com.appcenter.marketplace.domain.tempMarket.dto.req;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TempMarketReq {

    @NotBlank(message = "카테고리 입력은 필수 입력값입니다.")
    private String category;

    @NotBlank(message = "매장명을 필수입력값입니다.")
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
