package com.appcenter.marketplace.domain.metro;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMetro is a Querydsl query type for Metro
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMetro extends EntityPathBase<Metro> {

    private static final long serialVersionUID = 2031907650L;

    public static final QMetro metro = new QMetro("metro");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QMetro(String variable) {
        super(Metro.class, forVariable(variable));
    }

    public QMetro(Path<? extends Metro> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMetro(PathMetadata metadata) {
        super(Metro.class, metadata);
    }

}

