package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import com.pickupluck.ecogging.domain.plogging.entity.Reviewfile;
import com.pickupluck.ecogging.domain.plogging.repository.ReviewFileRepository;
import com.pickupluck.ecogging.domain.plogging.repository.ReviewRepository;
import com.pickupluck.ecogging.util.PageInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewFileRepository reviewFileRepository;

    private final ModelMapper modelMapper;



    @Override
    public List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception{
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Review> pages=reviewRepository.findAllByType("후기",pageRequest,sortByCreateAtDesc);
//        Page<Review> pages = reviewRepository.findAll(pageRequest);

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

    @Override
    public void modifyReviewInfo(ReviewDTO reviewDTO) throws Exception {
        System.out.println("리뷰 수정 서비스 test");

    }

    @Override
    public String reviewImgUpload(MultipartFile file) throws Exception {
        System.out.println("리뷰 작성 서비스 test");
        if(file!=null && !file.isEmpty()){
            System.out.println("파일 있음");
        }
            String directory="C:/JSR/front-work/upload/";
            String filename=file.getOriginalFilename();

            File directoryFile=new File(directory);
            if(!directoryFile.exists()){
                directoryFile.mkdirs();
            }

            File file2=new File(directory+filename);
            file.transferTo(file2);

            Reviewfile reviewFileEntity=new Reviewfile();
            reviewFileEntity.setPath("C:/JSR/front-work/reviewUpload/"+filename);
            reviewFileEntity.setFileName(filename);
            reviewFileEntity.setCreatedAt(LocalDateTime.now());
            reviewFileRepository.save(reviewFileEntity);
            return "C:/JSR/front-work/reviewUpload/"+filename;

    }

    @Override
    public void reviewWrite(Map<String, String> res) throws Exception {
        System.out.println("리뷰작성서비스");

        String title=res.get("title");
        String content=res.get("content");

        Review reviewEntity=new Review();
        reviewEntity.setTitle(title);
        reviewEntity.setContent(content);
        reviewEntity.setType("후기");
        reviewEntity.setCreatedAt(LocalDateTime.now());
        reviewEntity.setUserId(123);
        reviewEntity.setAccompanyId(1);
        reviewRepository.save(reviewEntity);
    }

    @Override
    public void reviewModify(Map<String, String> res, long id) throws Exception{
        System.out.println("리뷰 수정 서비스");

        Review review=reviewRepository.findById(id).orElse(null);
        if(review==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

//        Review reviewEntity=new Review();
        review.setTitle(title);
        review.setContent(content);
        review.setType("후기");
        review.setCreatedAt(LocalDateTime.now());
        review.setUserId(123);
        review.setAccompanyId(1);
        reviewRepository.save(review);
    }

    @Override
    public void reviewDel(long id) throws Exception {
        System.out.println("리뷰 삭제 서비스");
        reviewRepository.deleteById(id);
    }

}
