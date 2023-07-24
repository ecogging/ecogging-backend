package com.pickupluck.ecogging.domain.plogging.repository;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.plogging.entity.QEvent;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class CommonRepository {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Page<Event> findBySaveFalseAndEndDateGraterThan(PageRequest pageRequest, OrderSpecifier<?> orderSpecifier, Boolean save, Date endDate) {
        QEvent event = QEvent.event;
        LocalDate currentDate = LocalDate.now(); // 현재 날짜를 얻어옵니다.

        List<Event> events = jpaQueryFactory.selectFrom(event)
                .where(event.save.eq(false).and(event.endDate.after(currentDate).or(event.endDate.eq(currentDate))))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();

        long totalCount = jpaQueryFactory.selectFrom(event)
                .where(event.save.eq(false).and(event.endDate.after(currentDate).or(event.endDate.eq(currentDate))))
                .fetchCount();

        return new PageImpl<>(events, pageRequest, totalCount);
    }

}
