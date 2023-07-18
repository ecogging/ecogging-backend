package com.pickupluck.ecogging.domain.plogging.service;
<<<<<<< HEAD
=======

>>>>>>> f0797a6645a3dab68d75dd4fbb1c78777cb5ef28
import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
