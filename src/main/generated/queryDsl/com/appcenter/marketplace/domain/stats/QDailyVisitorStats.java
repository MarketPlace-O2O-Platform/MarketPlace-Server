package com.appcenter.marketplace.domain.stats;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDailyVisitorStats is a Querydsl query type for DailyVisitorStats
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyVisitorStats extends EntityPathBase<DailyVisitorStats> {

    private static final long serialVersionUID = -1990828999L;

    public static final QDailyVisitorStats dailyVisitorStats = new QDailyVisitorStats("dailyVisitorStats");

    public final NumberPath<Long> activeMembers = createNumber("activeMembers", Long.class);

    public final NumberPath<Double> avgResponseTime = createNumber("avgResponseTime", Double.class);

    public final NumberPath<Long> couponDownloads = createNumber("couponDownloads", Long.class);

    public final NumberPath<Long> couponUsages = createNumber("couponUsages", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> errorCount = createNumber("errorCount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> newMemberSignups = createNumber("newMemberSignups", Long.class);

    public final NumberPath<Long> paybackDownloads = createNumber("paybackDownloads", Long.class);

    public final NumberPath<Long> totalPageViews = createNumber("totalPageViews", Long.class);

    public final NumberPath<Long> uniqueVisitors = createNumber("uniqueVisitors", Long.class);

    public QDailyVisitorStats(String variable) {
        super(DailyVisitorStats.class, forVariable(variable));
    }

    public QDailyVisitorStats(Path<? extends DailyVisitorStats> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDailyVisitorStats(PathMetadata metadata) {
        super(DailyVisitorStats.class, metadata);
    }

}

