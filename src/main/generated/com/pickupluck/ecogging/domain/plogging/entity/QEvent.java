package com.pickupluck.ecogging.domain.plogging.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -1641416659L;

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

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

