package com.pickupluck.ecogging.domain.plogging.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipation is a Querydsl query type for Participation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipation extends EntityPathBase<Participation> {

    private static final long serialVersionUID = -329527340L;

    public static final QParticipation participation = new QParticipation("participation");

    public final NumberPath<Integer> accompanyId = createNumber("accompanyId", Integer.class);

    public final BooleanPath confirm = createBoolean("confirm");

    public final NumberPath<Integer> eventId = createNumber("eventId", Integer.class);

    public final NumberPath<Integer> participationId = createNumber("participationId", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QParticipation(String variable) {
        super(Participation.class, forVariable(variable));
    }

    public QParticipation(Path<? extends Participation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipation(PathMetadata metadata) {
        super(Participation.class, metadata);
    }

}

