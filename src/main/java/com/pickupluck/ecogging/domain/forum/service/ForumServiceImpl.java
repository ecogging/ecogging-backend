package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.dto.MyForumShareResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.ForumFile;
import com.pickupluck.ecogging.domain.forum.repository.ForumFileRepository;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.PageInfo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class ForumServiceImpl implements ForumService{


    @Autowired
    AccompanyRepository accompanyRepository;
    @Autowired
    private ForumRepository forumRepository; // 포럼 레포지
    @Autowired
    private ForumFileRepository forumFileRepository; // 포럼파일 레포지
    @Autowired
    private UserRepository userRepository; // 유저 레포지
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception{
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

        List<ReviewDTO> list=new ArrayList<>();
//        for(Forum forum:pages.getContent()){
//            list.add(modelMapper.map(forum, ForumDto.class));
//        }
        return list;
    }

    @Override
    public Map<String, Object> getMyForumList(Long userId, Integer page, String order) throws Exception {
        Sort.Direction sort = Sort.Direction.DESC;
        if(order.equals("old")) {
            sort = Sort.Direction.ASC;
        }
        PageRequest pageRequest=PageRequest.of(page-1, 10, Sort.by(sort,"forumId"));
        Page<Forum> pages=forumRepository.findAllByWriterId(userId, pageRequest);

        Map<String,Object> map = new HashMap<>();
        List<ForumDTO> list=new ArrayList<>();
        for(Forum forum:pages.getContent()){
            list.add(modelMapper.map(forum, ForumDTO.class));
        }

        map.put("list", list);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setAllPage(pages.getTotalPages());
        pageInfo.setCurPage(page);
        int startPage=(page-1)/10*10+1;
        int endPage=startPage+10-1;
        if(endPage>pageInfo.getAllPage()){
            endPage=pageInfo.getAllPage();
        }
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        map.put("pageInfo", pageInfo);
        return map;

    }

    // RouteServiceImpl ---------------------------------------------------------------------------------

    @Override
    public List<ForumDTO> getRoutes(Integer page, PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Forum> pages=forumRepository.findAllByType("경로",pageRequest,sortByCreateAtDesc);
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

        List<ForumDTO> list = new ArrayList<>();
        for(Forum route : pages.getContent()) {
            list.add(modelMapper.map(route, ForumDTO.class));
        }
        return list;
    }

    @Override
    public void routeWrite(Map<String, String> res) throws Exception {
        System.out.println("루틴작성서비스");

        String title=res.get("title");
        String content=res.get("content");
        String routeLocation=res.get("routeLocation");

//        forumRepository.save(routeEntity);
        Forum routeEntity= Forum.builder()
                .title(title)
                .content(content)
                .type("경로")
                .writer((userRepository.findById(1L).get()))
                .routeLocation(routeLocation)
                .build();
        forumRepository.save(routeEntity);
    }

    @Override
    public ForumDTO getRouteInfo(Long id) throws Exception {
        Optional<Forum> routeInfo=forumRepository.findById(id);
        if(routeInfo.isEmpty()) return null;

        // 작성자 가져오기
        Forum route=routeInfo.get();
        Optional<User> writerOpt = userRepository.findById(route.getWriter().getId());
        User writer = writerOpt.get();

        // DTO 생성
        ForumDTO getRoute= ForumDTO.builder()
                .forumId(route.getId())
                .forumType(route.getType())
                .title(route.getTitle())
                .content(route.getContent())
                .views(route.getViews()+1)
                .isTemp(route.getIsTemporary())
                .routeLocation(route.getRouteLocation())
                .routeLocationDetail(route.getRouteLocationDetail())
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .writerPic(writer.getProfileImageUrl())
                .build();

//        route.setViews(route.getViews()+1);
        forumRepository.save(route);
//        return getRoute;
        return null;
    }


    // ShareServiceImpl ------------------------------------------------------------------------------------

    @Override
    public List<ForumDTO> getShares(Integer page, PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(page - 1, 5);

        //Page<Event> pages = eventRepository.findBySaveFalse(pageRequest);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Forum> pages = forumRepository.findAllByType("나눔",pageRequest,sortByCreateAtDesc);

        pageInfo.setAllPage(pages.getTotalPages());

        // 현재 페이지가 마지막 페이지인 경우 다음 페이지로 이동하지 않음
        if (page > pageInfo.getAllPage()) {
            return Collections.emptyList();
        }

        pageInfo.setCurPage(page);
        int startPage = (page - 1) / 5 * 5 + 1;
        int endPage = startPage + 5 - 1;
        if (endPage > pageInfo.getAllPage()) endPage = pageInfo.getAllPage();
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        // boolean isLastPage = page >= pageInfo.getAllPage(); // 현재 페이지가 마지막 페이지인지 여부 판단
        //pageInfo.setIsLastPage(isLastPage); // isLastPage 값을 설정

        List<ForumDTO> list = new ArrayList<>();
        for (Forum share : pages.getContent()) {
            list.add(modelMapper.map(share, ForumDTO.class));
        }
        return list;
    }

    @Override
    public String shareImgUpload(MultipartFile file) throws Exception {
        System.out.println("나눔 작성 서비스 test");
        if(file!=null && !file.isEmpty()){
            System.out.println("파일 있음");
        }
        String directory="C:/JSR/front-work/shareUpload/";
        String filename=file.getOriginalFilename();

        File directoryFile=new File(directory);
        if(!directoryFile.exists()){
            directoryFile.mkdirs();
        }

        File file2=new File(directory+filename);
        file.transferTo(file2);

        ForumFile shareFileEntity= ForumFile.builder()
                .fileName(filename)
                .path("C:/JSR/front-work/shareUpload/"+filename)
                .build();
//        shareFileEntity.setPath("C:/JSR/front-work/shareUpload/"+filename);
//        shareFileEntity.setFileName(filename);
//        shareFileEntity.setCreatedAt(LocalDateTime.now());
        forumFileRepository.save(shareFileEntity);
        return "C:/JSR/front-work/upload/"+filename;
    }

    @Override
    public ForumDTO getShareInfo(Long id) throws Exception {
        Optional<Forum> shareInfo=forumRepository.findById(id);
        if(shareInfo.isEmpty()) return null;
        Forum share=shareInfo.get();

        // 작성자
        Optional<User> writerOpt = userRepository.findById(share.getWriter().getId());
        User writer = writerOpt.get();

        // 첨부파일
        Optional<ForumFile> fileOpt = forumFileRepository.findById(Long.parseLong(share.getFileId()+""));
        ForumFile file = fileOpt.get();

        // DTO 생성
        ForumDTO getShare= ForumDTO.builder()
                .forumId(share.getId())
                .forumType(share.getType())
                .title(share.getTitle())
                .content(share.getContent())
                .views(Integer.parseInt(share.getViews()+""))
                .isTemp(share.getIsTemporary())
                .fileId(file.getId())
                .fileName(file.getFileName())
                .filePath(file.getPath())
                .build();

//        ForumDTO getShare=ForumDTO.builder()
//                .forumId(share.getId())
//                        .views(share.getViews()+1);



//        share.setViews(share.getViews()+1);
        forumRepository.save(share);
        return getShare;
    }

    @Override
    public void shareDel(long id) throws Exception {
        System.out.println("리뷰 삭제 서비스");
        forumRepository.deleteById(id);
    }

    @Override
    public void shareModify(Map<String, String> res, long id) throws Exception {
        System.out.println("리뷰 수정 서비스");

        Forum share=forumRepository.findById(id).orElse(null);
        if(share==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

//        Review reviewEntity=new Review();
        share = Forum.builder()
                .title(title)
                .content(content)
                .type("나눔")
                .writer(userRepository.findById(1L).get())
                .build();
//        share.setTitle(title);
//        share.setContent(content);
//        share.setType("나눔");
//        share.setCreatedAt(LocalDateTime.now());
//        share.setUserId(123);
//        share.setAccompanyId(1);
        forumRepository.save(share);
    }

    @Override
    public void shareWrite(Map<String, String> res) throws Exception {
        System.out.println("나눔작성서비스");

        String title=res.get("title");
        String content=res.get("content");

        Forum shareEntity=Forum.builder()
                .title(title)
                .content(content)
                .type("나눔")
                .writer(userRepository.findById(1L).get())
                .build();
//        shareEntity.setTitle(title);
//        shareEntity.setContent(content);
//        shareEntity.setType("나눔");
//        shareEntity.setCreatedAt(LocalDateTime.now());
//        shareEntity.setUserId(123);
//        shareEntity.setAccompanyId();
        forumRepository.save(shareEntity);
    }




    // ReviewServiceImpl ------------------------------------------------------------------------------------
    @Override
    public List<ReviewDTO> getReviewsRv(Integer page, PageInfo pageInfo) throws Exception{
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Forum> pages=forumRepository.findAllByType("후기",pageRequest,sortByCreateAtDesc);
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
        for(Forum review : pages.getContent()) {
            list.add(modelMapper.map(review, ReviewDTO.class));
        }
        return list;
    }

    @Override
    public ReviewDTO getReviewInfo(Long id) throws Exception {
        Optional<Forum> reviewInfo=forumRepository.findById(id);
        if(reviewInfo.isEmpty()) return null;

        Forum review=reviewInfo.get();
        ReviewDTO getReview=new ReviewDTO(review);

        review.setViews(review.getViews()+1);
        forumRepository.save(review);
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

        ForumFile reviewFileEntity= ForumFile.builder()
                .path("C:/JSR/front-work/reviewUpload/"+filename)
                .fileName(filename)
                .build();
//        reviewFileEntity.setPath("C:/JSR/front-work/reviewUpload/"+filename);
//        reviewFileEntity.setFileName(filename);
//        reviewFileEntity.setCreatedAt(LocalDateTime.now());
        forumFileRepository.save(reviewFileEntity);
        return "C:/JSR/front-work/reviewUpload/"+filename;

    }

    @Override
    public void reviewWrite(Map<String, String> res) throws Exception {
        System.out.println("리뷰작성서비스");


        String title=res.get("title");
        String content=res.get("content");

        Forum reviewEntity=Forum.builder()
                .title(title)
                .content(content)
                .type("후기")
                .writer(userRepository.findById(1L).get())
                .thisAccompany(accompanyRepository.findById(1L).get())
                .build();

//        reviewEntity.setTitle(title);
//        reviewEntity.setContent(content);
//        reviewEntity.setType("후기");
//        reviewEntity.setCreatedAt(LocalDateTime.now());
//        reviewEntity.setUserId(123);
//        reviewEntity.setAccompanyId();
//        reviewRepository.save(reviewEntity);
    }

    @Override
    public void reviewModify(Map<String, String> res, long id) throws Exception{
        System.out.println("리뷰 수정 서비스");

        Forum review=forumRepository.findById(id).orElse(null);
        if(review==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

        Forum reviewEntity=Forum.builder()
                .title(title)
                .content(content)
                .type("후기")
                .writer(userRepository.findById(1L).get())
                .thisAccompany(accompanyRepository.findById(1L).get())
                .build();
//        Review reviewEntity=new Review();
//        review.setTitle(title);
//        review.setContent(content);
//        review.setType("후기");
//        review.setCreatedAt(LocalDateTime.now());
//        review.setUserId(123);
//        review.setAccompanyId(1);
//        reviewRepository.save(review);
    }

    @Override
    public void reviewDel(long id) throws Exception {
        System.out.println("리뷰 삭제 서비스");
        forumRepository.deleteById(id);
    }




    // Main Forums ---------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Page<MainForumsResponseDto> getMainForums(Pageable pageable) {

        // 데이터 확보
        Page<Forum> latestForumsThree = forumRepository.findAllWithoutTemp(pageable);

        // Entity -> DTO
        Page<MainForumsResponseDto> latestForumsToDto = latestForumsThree.map(fr -> {
            return MainForumsResponseDto.builder()
                    .forumId(fr.getId())
                    .type(fr.getType())
                    .title(fr.getTitle())
                    .content(fr.getContent())
                    .createdAt(fr.getCreatedAt())
                    .views(fr.getViews())
                    .writerId(fr.getWriter().getId())
                    .writerNickname(fr.getWriter().getNickname())
                    .build();
        });

        return latestForumsToDto;
    }

    // MyForum(SHARE) ----------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Page<MyForumShareResponseDto> getMyShares(Long userId, Pageable pageable) {

        // 데이터 확보
        Page<Forum> mySharesEntity = forumRepository.findAllByUserIdAndType(userId, pageable);

        // Entity -> DTO
        Page<MyForumShareResponseDto> mySharesDto = mySharesEntity.map(share -> {
            Optional<ForumFile> shareFile = Optional.empty(); // 초기화

            if(share.getFileId() != null) {
                // 첨부파일
                shareFile = forumFileRepository.findById(share.getFileId());
            }

            if(shareFile.isEmpty()){
                return MyForumShareResponseDto.builder()
                        .forumId(share.getId())
                        .title(share.getTitle())
                        .content(share.getContent())
                        .createdAt(share.getCreatedAt())
                        .views(share.getViews())
                        .fileName(null)
                        .filePath(null)
                        .build();
            } else {
                return MyForumShareResponseDto.builder()
                        .forumId(share.getId())
                        .title(share.getTitle())
                        .content(share.getContent())
                        .createdAt(share.getCreatedAt())
                        .views(share.getViews())
                        .fileName(shareFile.get().getFileName())
                        .filePath(shareFile.get().getPath())
                        .build();
            }
        });

        return mySharesDto;
    }
}
