package com.appcenter.marketplace.domain.beta;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBetaCoupon is a Querydsl query type for BetaCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBetaCoupon extends EntityPathBase<BetaCoupon> {

    private static final long serialVersionUID = -1958237838L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBetaCoupon betaCoupon = new QBetaCoupon("betaCoupon");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    public final QBetaMarket betaMarket;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExpired = createBoolean("isExpired");

    public final BooleanPath isUsed = createBoolean("isUsed");

    public final com.appcenter.marketplace.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QBetaCoupon(String variable) {
        this(BetaCoupon.class, forVariable(variable), INITS);
    }

    public QBetaCoupon(Path<? extends BetaCoupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBetaCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBetaCoupon(PathMetadata metadata, PathInits inits) {
        this(BetaCoupon.class, metadata, inits);
    }

    public QBetaCoupon(Class<? extends BetaCoupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.betaMarket = inits.isInitialized("betaMarket") ? new QBetaMarket(forProperty("betaMarket"), inits.get("betaMarket")) : null;
        this.member = inits.isInitialized("member") ? new com.appcenter.marketplace.domain.member.QMember(forProperty("member")) : null;
    }

}

