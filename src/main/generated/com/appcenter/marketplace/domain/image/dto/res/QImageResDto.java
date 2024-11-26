package com.appcenter.marketplace.domain.image.dto.res;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.image.dto.res.QImageResDto is a Querydsl Projection type for ImageResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QImageResDto extends ConstructorExpression<ImageRes> {

    private static final long serialVersionUID = 796700072L;

    public QImageResDto(com.querydsl.core.types.Expression<Long> imageId, com.querydsl.core.types.Expression<Integer> sequence, com.querydsl.core.types.Expression<String> name) {
        super(ImageRes.class, new Class<?>[]{long.class, int.class, String.class}, imageId, sequence, name);
    }

}

