package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;

import java.util.List;
import java.util.Map;

public interface AccompanyService {

    Map<String, Object> getAccompanyList(Integer page, String orderby) throws Exception;
    void setAccompany(Integer temp, AccompanyDTO accompanyDTO) throws Exception;
}
