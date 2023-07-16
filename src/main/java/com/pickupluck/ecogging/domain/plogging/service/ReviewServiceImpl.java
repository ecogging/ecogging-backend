package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import com.pickupluck.ecogging.domain.plogging.repository.ReviewRepository;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception{
//        PageRequest pageRequest=PageRequest.of(page-1, 10);
//        Boolean save=null;
//
//        Page<Review> pages=commonRepository.findAll();
//        pageInfo.setAllPage((pages.getTotalPages()));
//        return null;



        PageRequest pageRequest = PageRequest.of(page-1, 5);

        //Page<Event> pages = eventRepository.findBySaveFalse(pageRequest);

        Page<Review> pages = reviewRepository.findAll(pageRequest);

        pageInfo.setAllPage(pages.getTotalPages());

        // 현재 페이지가 마지막 페이지인 경우 다음 페이지로 이동하지 않음
        if (page > pageInfo.getAllPage()) {
            return Collections.emptyList();
        }

        pageInfo.setCurPage(page);
        int startPage = (page-1)/5*5+1;
        int endPage = startPage+5-1;
        if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
       // boolean isLastPage = page >= pageInfo.getAllPage(); // 현재 페이지가 마지막 페이지인지 여부 판단
        //pageInfo.setIsLastPage(isLastPage); // isLastPage 값을 설정

        List<ReviewDTO> list = new ArrayList<>();
        for(Review review : pages.getContent()) {
            list.add(modelMapper.map(review, ReviewDTO.class));
        }
        return list;
    }

    @Override
    public ReviewDTO getReviewInfo(Long id) throws Exception {
        Optional<Review> reviewInfo=reviewRepository.findById(id);
        if(reviewInfo.isEmpty()) return null;
        Review review=reviewInfo.get();
        ReviewDTO getReview=new ReviewDTO(review);

        review.setViews(review.getViews()+1);
        reviewRepository.save(review);
        return getReview;
    }
}
