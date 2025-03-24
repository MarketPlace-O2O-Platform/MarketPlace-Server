package com.appcenter.marketplace.domain.local;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocal is a Querydsl query type for Local
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocal extends EntityPathBase<Local> {

    private static final long serialVersionUID = 299520614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocal local = new QLocal("local");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.appcenter.marketplace.domain.metro.QMetro metro;

    public final StringPath name = createString("name");

    public QLocal(String variable) {
        this(Local.class, forVariable(variable), INITS);
    }

    public QLocal(Path<? extends Local> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocal(PathMetadata metadata, PathInits inits) {
        this(Local.class, metadata, inits);
    }

    public QLocal(Class<? extends Local> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.metro = inits.isInitialized("metro") ? new com.appcenter.marketplace.domain.metro.QMetro(forProperty("metro")) : null;
    }

}

