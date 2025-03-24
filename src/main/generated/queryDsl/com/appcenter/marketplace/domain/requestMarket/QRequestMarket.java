package com.appcenter.marketplace.domain.requestMarket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequestMarket is a Querydsl query type for RequestMarket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequestMarket extends EntityPathBase<RequestMarket> {

    private static final long serialVersionUID = 795529702L;

    public static final QRequestMarket requestMarket = new QRequestMarket("requestMarket");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QRequestMarket(String variable) {
        super(RequestMarket.class, forVariable(variable));
    }

    public QRequestMarket(Path<? extends RequestMarket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequestMarket(PathMetadata metadata) {
        super(RequestMarket.class, metadata);
    }

}

