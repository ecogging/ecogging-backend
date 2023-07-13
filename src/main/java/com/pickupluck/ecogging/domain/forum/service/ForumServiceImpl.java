package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.util.PageInfo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ForumServiceImpl implements ForumService{

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ForumDto> getReviews(Integer page, PageInfo pageInfo) throws Exception{
//        List<Forum> list=forumRepository.findAll();
        PageRequest pageRequest=PageRequest.of(page-1, 10, Sort.by(Sort.Direction.DESC,"forumId"));


        //Page<Forum> pages=forumRepository.findRequest(pageRequest);



        //pageInfo.setAllPage(pages.getTotalPages());
        pageInfo.setCurPage(page);
        int startPage=(page-1)/5*5+1;
        int endPage=startPage+5-1;
        if(endPage>pageInfo.getAllPage()){
            endPage=pageInfo.getAllPage();
        }
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        List<ForumDto> list=new ArrayList<>();
//        for(Forum forum:pages.getContent()){
//            list.add(modelMapper.map(forum, ForumDto.class));
//        }
        return list;
    }
}
