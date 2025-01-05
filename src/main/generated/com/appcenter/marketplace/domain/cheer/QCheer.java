package com.appcenter.marketplace.domain.cheer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheer is a Querydsl query type for Cheer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheer extends EntityPathBase<Cheer> {

    private static final long serialVersionUID = 750709930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheer cheer = new QCheer("cheer");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final com.appcenter.marketplace.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.appcenter.marketplace.domain.tempMarket.QTempMarket tempMarket;

    public QCheer(String variable) {
        this(Cheer.class, forVariable(variable), INITS);
    }

    public QCheer(Path<? extends Cheer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheer(PathMetadata metadata, PathInits inits) {
        this(Cheer.class, metadata, inits);
    }

    public QCheer(Class<? extends Cheer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.appcenter.marketplace.domain.member.QMember(forProperty("member")) : null;
        this.tempMarket = inits.isInitialized("tempMarket") ? new com.appcenter.marketplace.domain.tempMarket.QTempMarket(forProperty("tempMarket"), inits.get("tempMarket")) : null;
    }

}

