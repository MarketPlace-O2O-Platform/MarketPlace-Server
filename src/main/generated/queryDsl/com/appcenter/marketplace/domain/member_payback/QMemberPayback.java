package com.appcenter.marketplace.domain.member_payback;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberPayback is a Querydsl query type for MemberPayback
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberPayback extends EntityPathBase<MemberPayback> {

    private static final long serialVersionUID = 786031571L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberPayback memberPayback = new QMemberPayback("memberPayback");

    public final com.appcenter.marketplace.global.common.QBaseEntity _super = new com.appcenter.marketplace.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isExpired = createBoolean("isExpired");

    public final BooleanPath isPayback = createBoolean("isPayback");

    public final com.appcenter.marketplace.domain.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.appcenter.marketplace.domain.payback.QPayback payback;

    public final StringPath receipt = createString("receipt");

    public QMemberPayback(String variable) {
        this(MemberPayback.class, forVariable(variable), INITS);
    }

    public QMemberPayback(Path<? extends MemberPayback> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberPayback(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberPayback(PathMetadata metadata, PathInits inits) {
        this(MemberPayback.class, metadata, inits);
    }

    public QMemberPayback(Class<? extends MemberPayback> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.appcenter.marketplace.domain.member.QMember(forProperty("member")) : null;
        this.payback = inits.isInitialized("payback") ? new com.appcenter.marketplace.domain.payback.QPayback(forProperty("payback"), inits.get("payback")) : null;
    }

}

