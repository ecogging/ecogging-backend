package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {

    private final AccompanyRepository accompanyRepository;
    private final ModelMapper modelMapper;
    @Override
    public Map<String, Object> getAccompanyList(Integer page, String orderby) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.DESC, orderby));
        Page<Accompany> accompanyPage = accompanyRepository.findAll(pageRequest);
        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Accompany accompany : accompanyPage.getContent()){
            AccompanyDTO accompanyDTO = modelMapper.map(accompany, AccompanyDTO.class);
            System.out.println(accompany.getWriter().getNickname());
            accompanyDTO.setNickname(accompany.getWriter().getNickname());
            list.add(accompanyDTO);
        }
        map.put("list", list);
        map.put("hasNext", accompanyPage.hasNext());
        return map;
    }

    @Override
    public void setAccompany(Integer temp, AccompanyDTO accompanyDTO) throws Exception {
        Accompany accompany = modelMapper.map(accompanyDTO, Accompany.class);
        accompany.setSave(temp==1);
        accompanyRepository.save(accompany);
    }

}
