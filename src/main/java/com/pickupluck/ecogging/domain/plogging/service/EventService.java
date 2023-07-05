package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface EventService {
    void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception;
    List<EventDTO> getEventList(Integer page, PageInfo pageInfo) throws Exception;
    Event getEvent(Integer eventId) throws Exception;
//    Map<String, Object> getEvent(Integer eventId, HttpServletRequest request) throws Exception;
    void readFile(String fileId, OutputStream out) throws Exception;
    void removeEvent(Integer eventId) throws Exception;
    void modifyEvent(EventDTO eventDTO) throws Exception;
    Integer scrapEvent(Integer eventId)throws Exception;

}
