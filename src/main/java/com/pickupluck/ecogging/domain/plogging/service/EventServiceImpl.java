package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.file.entity.File;
import com.pickupluck.ecogging.domain.file.repository.FileRepository;
import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.plogging.entity.QEvent;
import com.pickupluck.ecogging.domain.plogging.repository.CommonRepository;
import com.pickupluck.ecogging.domain.plogging.repository.EventRepository;
import com.pickupluck.ecogging.util.PageInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    private final ModelMapper modelMappper;

    private final FileRepository fileRepository;

    private  final CommonRepository commonRepository;

    private final String uploadDir = "D:/MJS/front-work/upload/";

    public void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception {
        if(file!=null && !file.isEmpty()) {
            String path="D:/MJS/front-work/upload/";
            String originName = file.getOriginalFilename();
            Long size = file.getSize();
            String fullPath = path+originName;

            com.pickupluck.ecogging.domain.file.entity.File fil = new com.pickupluck.ecogging.domain.file.entity.File();
            fil.setOriginName(originName);
            fil.setSize(size);
            fil.setFullPath(fullPath);
            fileRepository.save(fil);

            Long fileId = fileRepository.save(fil).getId();
            eventDTO.setFileId(fileId);
            java.io.File dfile = new java.io.File(fullPath);
            try {
                file.transferTo(dfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Event event = modelMappper.map(eventDTO, Event.class);
        eventRepository.save(event);
    }
    @Override
    public List<EventDTO> getEventList(Integer page, PageInfo pageInfo,  String sorttype) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, 5);
        Boolean save = null;
        Date endDate = null;

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

        Page<Event> pages = commonRepository.findBySaveFalseAndEndDateGraterThan(pageRequest, orderSpecifier, save, endDate);

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

        List<EventDTO> list = new ArrayList<>();
        for(Event event : pages.getContent()) {
            list.add(modelMappper.map(event, EventDTO.class));
        }
        return list;
    }



    @Override
    public Event getEvent(Integer eventId) throws Exception {
        Optional<Event> oevent = eventRepository.findById(eventId);
        if(oevent.isEmpty()) throw new Exception("이벤트 아이디 오류");
        return oevent.get();
    }

//    @Override
//    public Map<String, Object> getEvent(Integer eventId, HttpServletRequest request) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        Optional<Event> oevent = eventRepository.findById(eventId);
//        if(oevent.isEmpty()) throw new Exception("이벤트 아이디 오류");
//        Event event = oevent.get();
//        map.put("event", modelMappper.map(event,EventDTO.class));
//        // map.put("scrap", event.getS)
//
//        Integer id = (Integer) request.getAttribute("id");
//        Boolean scrap = false;
//        if(id!=null && id.equals("")) {
//            List<Scrap> scrapList = event.getScrap();
//            for(Scrap scrap : scrapList) {
//                if(scrap.get)
//            }
//        }
//
//        return map;
//    }

    public void readFile(Long fileId, OutputStream out) throws Exception {
        String path="D:/MJS/front-work/upload/";
        Optional<File> ofile = fileRepository.findById(fileId);
        if(ofile.isPresent()) {
            String fileName = ofile.get().getOriginName();
            FileInputStream fis = new FileInputStream(path+fileName);
            FileCopyUtils.copy(fis, out);
            out.flush();
        }
    }

    @Override
    public void removeEvent(Integer eventId) throws Exception {
        eventRepository.deleteById(eventId);
    }

    @Override
    public void modifyEvent(EventDTO eventDTO) throws Exception {
        Event event = modelMappper.map(eventDTO, Event.class);
        eventRepository.save(event);
    }

    @Override
    public Integer scrapEvent(Integer eventId) throws Exception {
        Optional<Event> oevent = eventRepository.findById(eventId);
        if(oevent.isEmpty()) throw new Exception("이벤트 번호 오류");
       // return oevent.get().getScrap().;
        return  null;
    }

    @Override
    public Integer updateView(Integer id) throws Exception {
        return this.eventRepository.updateView(id);
    }
}
