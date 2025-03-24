package com.appcenter.marketplace.domain.tempMarket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTempMarket is a Querydsl query type for TempMarket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTempMarket extends EntityPathBase<TempMarket> {

    private static final long serialVersionUID = 1836241580L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTempMarket tempMarket = new QTempMarket("tempMarket");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final com.appcenter.marketplace.domain.category.QCategory category;

    public final NumberPath<Integer> cheerCount = createNumber("cheerCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHidden = createBoolean("isHidden");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath thumbnail = createString("thumbnail");

    public QTempMarket(String variable) {
        this(TempMarket.class, forVariable(variable), INITS);
    }

    public QTempMarket(Path<? extends TempMarket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTempMarket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTempMarket(PathMetadata metadata, PathInits inits) {
        this(TempMarket.class, metadata, inits);
    }

    public QTempMarket(Class<? extends TempMarket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.appcenter.marketplace.domain.category.QCategory(forProperty("category")) : null;
    }

}

