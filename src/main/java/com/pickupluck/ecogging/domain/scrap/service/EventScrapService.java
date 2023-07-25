package com.pickupluck.ecogging.domain.scrap.service;

import com.pickupluck.ecogging.domain.scrap.dto.EventScrapDTO;
import com.pickupluck.ecogging.util.PageInfo;

import java.util.List;

public interface EventScrapService {
    List<EventScrapDTO> getEventScrapList(Integer page, PageInfo pageInfo, String sorttype) throws Exception;
}
