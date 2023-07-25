package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.dto.MainEventResponseDto;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface EventService {
    void writeEvent(EventDTO eventDTO, MultipartFile file) throws Exception;
    List<EventDTO> getEventList(Integer page, PageInfo pageInfo, String sorttype) throws Exception;
    EventDTO getEvent(Integer eventId) throws Exception;
    void readFile(Long fileId, OutputStream out) throws Exception;
    void removeEvent(Integer eventId) throws Exception;
    void modifyEvent(EventDTO eventDTO, MultipartFile file) throws Exception;
    Integer scrapEvent(Integer eventId)throws Exception;
    Integer updateView(Integer id) throws Exception;
    Boolean isEventScrap(Long userId, Integer eventId) throws Exception;
    Boolean toggleEventScrap(Long userId, Long eventId) throws Exception;
    Map<String, Object> getMyEventList(Long userId, Integer page) throws Exception;
    Map<String, Object> getMyEventscrapList(Long userId, Integer page) throws Exception;
    Map<String, Object> getMyEventTempList(Long userId, Integer page) throws Exception;


    // Main Events
    Page<MainEventResponseDto> getMainEvents(Pageable pageable);
}
