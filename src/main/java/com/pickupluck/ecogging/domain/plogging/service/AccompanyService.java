package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;

import java.util.Map;

public interface AccompanyService {

    Map<String, Object> getAccompanyList(Integer page, String orderby) throws Exception;
  
    void setAccompany(Integer temp, AccompanyDTO accompanyDTO) throws Exception;

    AccompanyDTO getAccompany(Long id) throws Exception;

    void removeAccompany(Long id) throws  Exception;

    Boolean isParticipation(Long userId, Long accompanyId) throws Exception;
  
    Boolean toggleParticipation(Long userId, Long accompanyId) throws Exception;

    Boolean isAccompanyScrap(Long userId, Long accompanyId) throws Exception;
  
    Boolean toggleAccompanyScrap(Long userId, Long accompanyId) throws Exception;

    Map<String, Object> getMainAccompanyList() throws Exception;

    Map<String, Object> getMyAccompanyList(Long userId, Integer page) throws Exception;

    Map<String, Object> getMyParticipationList(Long userId, Integer page) throws Exception;

    Map<String, Object> getMyAccompanyscrapList(Long userId, Integer page) throws Exception;

    Map<String, Object> getMyAccompanyTempList(Long userId, Integer page) throws Exception;

}
