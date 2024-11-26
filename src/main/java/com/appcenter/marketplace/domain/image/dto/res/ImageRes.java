package com.appcenter.marketplace.domain.image.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ImageRes {
    private final Long imageId;
    private final Integer sequence;
    private final String name;


    @QueryProjection
    public ImageRes(Long imageId, Integer sequence, String name) {
        this.imageId = imageId;
        this.sequence = sequence;
        this.name = name;
    }
}
