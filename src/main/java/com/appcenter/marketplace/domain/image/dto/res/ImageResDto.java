package com.appcenter.marketplace.domain.image.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ImageResDto {
    private final Long id;
    private final Integer order;
    private final String name;


    @QueryProjection
    public ImageResDto(Long id, Integer order, String name) {
        this.id = id;
        this.order = order;
        this.name = name;
    }
}
