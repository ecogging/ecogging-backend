package com.pickupluck.ecogging.domain.plogging.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -1641416659L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final com.pickupluck.ecogging.domain.QBaseEntity _super = new com.pickupluck.ecogging.domain.QBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath content = createString("content");

    public final StringPath corpName = createString("corpName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Integer> eventId = createNumber("eventId", Integer.class);

    public final StringPath explanation = createString("explanation");

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final StringPath location = createString("location");

    public final DatePath<java.time.LocalDate> meetingDate = createDate("meetingDate", java.time.LocalDate.class);

    public final BooleanPath save = createBoolean("save");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.pickupluck.ecogging.domain.user.entity.QUser userId;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new com.pickupluck.ecogging.domain.user.entity.QUser(forProperty("userId")) : null;
    }

}

