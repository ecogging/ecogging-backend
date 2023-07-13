package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface EventService {
    void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception;
    List<EventDTO> getEventList(Integer page, PageInfo pageInfo, String sort) throws Exception;
    EventDTO getEvent(Integer eventId) throws Exception;
//    Map<String, Object> getEvent(Integer eventId, HttpServletRequest request) throws Exception;
    void readFile(Long fileId, OutputStream out) throws Exception;
    void removeEvent(Integer eventId) throws Exception;
    void modifyEvent(EventDTO eventDTO) throws Exception;
    Integer scrapEvent(Integer eventId)throws Exception;
    Integer updateView(Integer id) throws Exception;
    Boolean isEventScrap(Long userId, Integer eventId) throws Exception;
    Boolean toggleEventScrap(Long userId, Integer eventId) throws Exception;
}
