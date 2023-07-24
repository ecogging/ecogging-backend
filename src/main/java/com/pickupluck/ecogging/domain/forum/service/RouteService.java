package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RouteService {
    List<ForumDTO> getRoutes(Integer page, PageInfo pageInfo) throws Exception;

    void routeWrite(Map<String, String> res) throws Exception;

    ForumDTO getRouteInfo(Long id) throws Exception;
}
