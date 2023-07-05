package com.pickupluck.ecogging.domain.message.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -1768661852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final com.pickupluck.ecogging.domain.QBaseEntity _super = new com.pickupluck.ecogging.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deletedByRcv = createBoolean("deletedByRcv");

    public final BooleanPath deletedBySnd = createBoolean("deletedBySnd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> read = createNumber("read", Integer.class);

    public final com.pickupluck.ecogging.domain.user.entity.QUser receiver;

    public final com.pickupluck.ecogging.domain.user.entity.QUser sender;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new com.pickupluck.ecogging.domain.user.entity.QUser(forProperty("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new com.pickupluck.ecogging.domain.user.entity.QUser(forProperty("sender")) : null;
    }

}

