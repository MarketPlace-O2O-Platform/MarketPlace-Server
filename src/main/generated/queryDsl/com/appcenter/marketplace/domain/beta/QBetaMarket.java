package com.appcenter.marketplace.domain.beta;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBetaMarket is a Querydsl query type for BetaMarket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBetaMarket extends EntityPathBase<BetaMarket> {

    private static final long serialVersionUID = -1684970104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBetaMarket betaMarket = new QBetaMarket("betaMarket");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    public final com.appcenter.marketplace.domain.category.QCategory category;

    public final StringPath couponDetail = createString("couponDetail");

    public final StringPath couponName = createString("couponName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath marketName = createString("marketName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QBetaMarket(String variable) {
        this(BetaMarket.class, forVariable(variable), INITS);
    }

    public QBetaMarket(Path<? extends BetaMarket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBetaMarket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBetaMarket(PathMetadata metadata, PathInits inits) {
        this(BetaMarket.class, metadata, inits);
    }

    public QBetaMarket(Class<? extends BetaMarket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.appcenter.marketplace.domain.category.QCategory(forProperty("category")) : null;
    }

}

