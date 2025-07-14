package com.appcenter.marketplace.domain.payback;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayback is a Querydsl query type for Payback
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayback extends EntityPathBase<Payback> {

    private static final long serialVersionUID = -2020859794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayback payback = new QPayback("payback");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isHidden = createBoolean("isHidden");

    public final com.appcenter.marketplace.domain.market.QMarket market;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QPayback(String variable) {
        this(Payback.class, forVariable(variable), INITS);
    }

    public QPayback(Path<? extends Payback> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayback(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayback(PathMetadata metadata, PathInits inits) {
        this(Payback.class, metadata, inits);
    }

    public QPayback(Class<? extends Payback> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.market = inits.isInitialized("market") ? new com.appcenter.marketplace.domain.market.QMarket(forProperty("market"), inits.get("market")) : null;
    }

}

