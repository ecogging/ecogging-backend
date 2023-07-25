package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.file.entity.File;
import com.pickupluck.ecogging.domain.file.repository.FileRepository;
import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.dto.MainEventResponseDto;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.plogging.entity.QEvent;
import com.pickupluck.ecogging.domain.plogging.repository.CommonRepository;
import com.pickupluck.ecogging.domain.plogging.repository.EventRepository;
import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import com.pickupluck.ecogging.domain.scrap.repository.EventscrapRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.PageInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private  final UserRepository userRepository;

    private  final EventscrapRepository eventscrapRepository;

    private final String uploadDir = "D:/MJS/front-work/upload/";
//    private final String uploadDir="C:/JSR/front-work/upload/"; dongur2 임시 경로

    public void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception {
        if(file!=null && !file.isEmpty()) {
            String path="D:/MJS/front-work/upload/";
//            String path="C:/JSR/front-work/upload/"; dongur2 임시 경로
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
        Optional<User> ouser = userRepository.findById(eventDTO.getUserId());
        if(ouser.isPresent()) {
            event.setUser(ouser.get());
        }
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
    public EventDTO getEvent(Integer eventId) throws Exception {
        Optional<Event> oevent = eventRepository.findById(eventId);
        if(oevent.isEmpty()) return null;
        Event event = oevent.get();
        EventDTO eventDTO = new EventDTO(event);

        event.setViews(event.getViews()+1);
        eventRepository.save(event);
        return eventDTO;
    }

    public void readFile(Long fileId, OutputStream out) throws Exception {
        String path="D:/MJS/front-work/upload/";
//        String path="C:/JSR/front-work/upload/"; dongur2 임시 경로
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
    public void modifyEvent(EventDTO eventDTO, MultipartFile file) throws Exception {
        if(file!=null && !file.isEmpty()) {
            String path="D:/MJS/front-work/upload/";
//            String path="C:/JSR/front-work/upload/"; dongur2 임시 경로
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
        Optional<User> user = userRepository.findById(eventDTO.getUserId());
        if(user.isPresent()) {
            event.setUser(user.get());
        }
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

//    @Override
//    public Boolean isEventScrap(Long userId, Integer eventId) throws Exception {
//        User user = userRepository.findById(userId).get();
//        Event event = eventRepository.findById(eventId).get();
//        Optional<Eventscrap> eventscrap = eventscrapRepository.findByUserScrapAndEventScrap(user, event);
//        if(eventscrap.isPresent()) return true;
//        else return false;
//    }
    @Override
    public Boolean isEventScrap(Long userId, Integer eventId) throws Exception {
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        Optional<Eventscrap> eventscrap = eventscrapRepository.findByUserScrapAndEventScrap(user,event);
        if (eventscrap.isPresent()) return true;
          else return false;
    }

    @Override
    public Boolean toggleEventScrap(Long userId, Long eventId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Event event = eventRepository.findById(Math.toIntExact(eventId)).orElseThrow(() -> new Exception("Event not found"));
        Optional<Eventscrap> eventscrap = eventscrapRepository.findByUserScrapAndEventScrap(user, event);

        if(eventscrap.isEmpty()) {
            eventscrapRepository.save(new Eventscrap(null, user, event));
            return true;
        } else {
            eventscrapRepository.deleteById(eventscrap.get().getScrapId());
            return false;
        }
    }


    // Main Events
    @Override
    @Transactional(readOnly = true)
    public Page<MainEventResponseDto> getMainEvents(Pageable pageable){

        // 데이터 확보
        Page<Event> latestEventsFour = eventRepository.findAllWithoutTemp(pageable);
        // Entity -> DTO
        Page<MainEventResponseDto> latesEventsToDto = latestEventsFour.map(evnt -> {
            return MainEventResponseDto.builder()
                    .evtid(evnt.getEventId().longValue())
                    .evtTitle(evnt.getTitle())
                    .evtStartDate(evnt.getMeetingDate())
                    .evtEndDate(evnt.getEndDate())
                    .active(evnt.getActive())
                    .evtLocation(evnt.getLocation())
                    .nickname(evnt.getCorpName())
                    .fileId(evnt.getFileId())
                    .filePath(fileRepository.findById(evnt.getFileId()).get().getFullPath())
                    .build();
        });

        return latesEventsToDto;
    }





}
