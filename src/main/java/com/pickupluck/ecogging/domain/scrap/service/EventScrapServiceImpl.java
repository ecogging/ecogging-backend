package com.pickupluck.ecogging.domain.scrap.service;

import com.pickupluck.ecogging.domain.plogging.entity.QEvent;
import com.pickupluck.ecogging.domain.plogging.repository.CommonRepository;
import com.pickupluck.ecogging.domain.scrap.dto.EventScrapDTO;
import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import com.pickupluck.ecogging.util.PageInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventScrapServiceImpl implements EventScrapService {

    private  final CommonRepository commonRepository;

    private final ModelMapper modelMappper;

    @Override
    public List<EventScrapDTO> getEventScrapList(Integer page, PageInfo pageInfo, String sorttype) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        OrderSpecifier<?> orderSpecifier = null;
        if(sorttype.equals("latest")) {
            orderSpecifier = new OrderSpecifier(Order.DESC, QEvent.event.createdAt);
        } else if(sorttype.equals("oldest")) {
            orderSpecifier = new OrderSpecifier(Order.ASC, QEvent.event.createdAt);
        } else if(sorttype.equals("popular")) {
            orderSpecifier = new OrderSpecifier(Order.DESC, QEvent.event.views);
        } else if(sorttype.equals("upcoming")) {
            orderSpecifier = new OrderSpecifier(Order.ASC, QEvent.event.meetingDate);
        } else {
            orderSpecifier = new OrderSpecifier(Order.DESC, QEvent.event.eventId);
        }

        //Page<Event> pages = eventRepository.findBySaveFalse(pageRequest);

        Page<Eventscrap> pages = commonRepository.findByScrap(pageRequest, orderSpecifier);

        pageInfo.setAllPage(pages.getTotalPages());

        // 현재 페이지가 마지막 페이지인 경우 다음 페이지로 이동하지 않음
        if (page > pageInfo.getAllPage()) {
            return Collections.emptyList();
        }

        pageInfo.setCurPage(page);
        int startPage = (page-1)/2*2+1;
        int endPage = startPage+2-1;
        if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        boolean isLastPage = page >= pageInfo.getAllPage(); // 현재 페이지가 마지막 페이지인지 여부 판단
        pageInfo.setIsLastPage(isLastPage); // isLastPage 값을 설정

        List<EventScrapDTO> list = new ArrayList<>();
        for(Eventscrap eventscrap : pages.getContent()) {
            list.add(modelMappper.map(eventscrap, EventScrapDTO.class));
        }
        return list;
    }
}
