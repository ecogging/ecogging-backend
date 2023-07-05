package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.plogging.repository.EventRepository;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    private final ModelMapper modelMappper;

    private final String uploadDir = "D:/MJS/front-work/upload/";

    @Override
    public void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception {
        if(file!=null && !file.isEmpty()) {
            String path="D:/MJS/front-work/upload/";
            String fileName = file.getOriginalFilename();
            File dfile = new File(path+fileName);
            file.transferTo(dfile);
            eventDTO.setFileId(Integer.valueOf(file.getOriginalFilename()));
        }
        Event event = modelMappper.map(eventDTO, Event.class);
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> getEventList(Integer page, PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, 16, Sort.by(Sort.Direction.DESC, "eventId"));
        Page<Event> pages = eventRepository.findAll(pageRequest);

        pageInfo.setAllPage(pages.getTotalPages());
        pageInfo.setCurPage(page);
        int startPage = (page-1)/10*10+1;
        int endPage = startPage+10-1;
        if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

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

    @Override
    public Integer createFile(MultipartFile file) throws Exception {
//        if (file == null || file.isEmpty()) {
//            throw new Exception("file'"+ file.getName() + "' is null or empty");
//        }
//        Integer filNum = 0;
//        FileDTO fil = new FileDTO();
//
//        fil.setFilClassification(file.getContentType());
//        fil.setFilOrgName(file.getOriginalFilename());
//        fil.setFilSaveName(file.getName());
//        fil.setFilSize(file.getSize());
//        filNum = fileDAO.selectNewFileId();
//        fil.setFilNum(filNum);
//        fileDAO.insertFile(fil);
//
//
//        File dfile = new File(servletContext.getRealPath(uploadDir) + filNum);
//
//        file.transferTo(dfile);
//
//        return filNum;
        return  null;

    }

    @Override
    public void readFile(String fileId, OutputStream out) throws Exception {
        String path="D:/MJS/front-work/upload/";
        FileInputStream fis = new FileInputStream(path+fileId);
        FileCopyUtils.copy(fis, out);
        out.flush();
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
}
