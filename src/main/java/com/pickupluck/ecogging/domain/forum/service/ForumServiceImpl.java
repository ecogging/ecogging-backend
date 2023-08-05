package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.*;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.ForumFile;
import com.pickupluck.ecogging.domain.forum.repository.ForumFileRepository;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import com.pickupluck.ecogging.domain.scrap.entity.ForumScrap;
import com.pickupluck.ecogging.domain.scrap.repository.ForumscrapRepository;
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
    @Autowired
    private ForumscrapRepository forumscrapRepository;

    @Override
    public Map<String, Object> getReviews(Pageable pageable) throws Exception {
        // 조건에 맞는 데이터 확보 ( 조건: 5개, 최신순 )
        String thisType = "후기";
//            Page<Forum> routesEntity = forumRepository.findAllByType(thisType,pageable);
        Page<Forum> reviewEntity = forumRepository.findByType(thisType,pageable);
        System.out.println(reviewEntity);
        long count = reviewEntity.getTotalElements();


        // Entity -> DTO
        Page<ForumDTO> reviewDto = reviewEntity.map(review -> {
            return ForumDTO.builder()
                    .forumId(review.getId())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .views(review.getViews())
                    .routeLocation(review.getRouteLocation())
                    .routeLocation(review.getRouteLocation())
                    .writerNickname(review.getWriter().getNickname())
                    .build();
        });

        // 결과 담아서 넘기는 맵
        Map<String, Object> result = new HashMap<>();
        result.put("res", reviewDto); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;
    }

    @Override
    public Map<String, Object> getMyForumList(Long userId, Integer page, String order) throws Exception {
        Sort.Direction sort = Sort.Direction.DESC;
        if(order.equals("old")) {
            sort = Sort.Direction.ASC;
        }
        PageRequest pageRequest=PageRequest.of(page-1, 5, Sort.by(sort,"id"));
        Page<Forum> pages=forumRepository.findByIsTemporaryFalseAndTypeAndWriterId("후기", userId, pageRequest);

        Map<String,Object> map = new HashMap<>();
        List<ForumDTO> list=new ArrayList<>();
        for(Forum forum:pages.getContent()){
            System.out.println(forum);
            list.add(new ForumDTO(forum));
        }

        map.put("list", list);

        Long allCount = pages.getTotalElements();
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
        map.put("allCount", allCount);
        return map;

    }

    @Override
    public void routeWrite(Map<String, String> res, Long userId, Boolean temp) throws Exception {
        System.out.println("루틴작성서비스");

        String title=res.get("title");
        String content=res.get("content");
        String routeLocation=res.get("routeLocation");

        Forum routeEntity= Forum.builder()
                .title(title)
                .content(content)
                .type("경로")
                .writer((userRepository.findById(userId).get()))
                .routeLocation(routeLocation)
                .isTemporary(temp)
                .views(0)
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

        int views=route.getViews();
        route.setViews(views+1);
        forumRepository.save(route);

        // DTO 생성
        ForumDTO getRoute= ForumDTO.builder()
                .forumId(route.getId())
                .forumType(route.getType())
                .title(route.getTitle())
                .content(route.getContent())
                .views(views+1)
                .isTemp(route.getIsTemporary())
                .routeLocation(route.getRouteLocation())
                .routeLocationDetail(route.getRouteLocationDetail())
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .writerPic(writer.getProfileImageUrl())
                .build();
        return getRoute;
    }

    @Override
    public void routeModify(ForumDTO forumDTO, Long userId, Long id, Boolean temp) throws Exception {
        System.out.println("루틴수정서비스");
        System.out.println(forumDTO);
        System.out.println("userId : "+userId);
        System.out.println("id : "+id);
        User user = userRepository.findById(userId).get();
        Forum routeEntity= Forum.builder()
                .id(forumDTO.getForumId())
                .title(forumDTO.getTitle())
                .content(forumDTO.getContent())
                .type(forumDTO.getForumType())
                .writer(user)
                .routeLocation(forumDTO.getRouteLocation())
                .routeLocationDetail(forumDTO.getRouteLocationDetail())
                .isTemporary(temp)
                .views(forumDTO.getViews())
//                .createdAt
                .build();
        forumRepository.save(routeEntity);
    }



    // ShareServiceImpl ------------------------------------------------------------------------------------

    @Override
    public Map<String, Object> getShares(Pageable pageable) throws Exception {

        // 조건에 맞는 데이터 확보 ( 조건: 5개, 최신순 )
        String thisType = "나눔";
//            Page<Forum> routesEntity = forumRepository.findAllByType(thisType,pageable);
        Page<Forum> sharesEntity = forumRepository.findByType(thisType,pageable);
        System.out.println(sharesEntity);
        long count = sharesEntity.getTotalElements();


        // Entity -> DTO
        Page<ForumDTO> sharesDto = sharesEntity.map(share -> {
            return ForumDTO.builder()
                    .forumId(share.getId())
                    .title(share.getTitle())
                    .content(share.getContent())
                    .createdAt(share.getCreatedAt())
                    .views(share.getViews())
                    .status(share.getStatus())
                    .routeLocation(share.getRouteLocation())
                    .routeLocation(share.getRouteLocation())
                    .writerNickname(share.getWriter().getNickname())
                    .build();
        });

        // 결과 담아서 넘기는 맵
        Map<String, Object> result = new HashMap<>();
        result.put("res", sharesDto); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;
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
        forumFileRepository.save(shareFileEntity);
        return "Http://localhost:8080/upload/"+filename;
    }

    @Override
    public ForumDTO getShareInfo(Long id) throws Exception {
        Optional<Forum> shareInfo=forumRepository.findById(id);
        if(shareInfo.isEmpty()) return null;
        Forum share=shareInfo.get();

        // 작성자
        Optional<User> writerOpt = userRepository.findById(share.getWriter().getId());
        User writer = writerOpt.get();

        int views=share.getViews();
        share.setViews(views+1);
        forumRepository.save(share);

        // DTO 생성
        ForumDTO getShare= ForumDTO.builder()
                .forumId(share.getId())
                .forumType(share.getType())
                .title(share.getTitle())
                .content(share.getContent())
                .views(views+1)
                .status(share.getStatus())
                .isTemp(share.getIsTemporary())
                .createdAt(share.getCreatedAt())
                .build();

        return getShare;
    }

    @Override
    public void shareDel(long id) throws Exception {
        System.out.println("리뷰 삭제 서비스");
        forumRepository.deleteById(id);
    }

    @Override
    public void shareModify(Map<String, String> res, Long id) throws Exception {
        System.out.println("나눔 수정 서비스");

        Forum share=forumRepository.findById(id).orElse(null);
        if(share==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

        share = Forum.builder()
                .id(id)
                .title(title)
                .content(content)
                .type("나눔")
                .writer(userRepository.findById(1L).get())
                .isTemporary(false)
                .views(0)
                .build();
        forumRepository.save(share);
    }

    @Override
    public void shareWrite(Map<String, String> res, Long userId, Boolean temp) throws Exception {
        System.out.println("나눔작성서비스");

        String title=res.get("title");
        String content=res.get("content");

        Forum shareEntity=Forum.builder()
                .title(title)
                .content(content)
                .type("나눔")
                .status("진행중")
                .writer(userRepository.findById(userId).get())
                .isTemporary(temp)
                .views(0)
                .build();
        forumRepository.save(shareEntity);
    }

    // routes
    @Override
    public Map<String, Object> getRoutes(Pageable pageable) throws Exception {

            // 조건에 맞는 데이터 확보 ( 조건: 5개, 최신순 )
            String thisType = "경로";
//            Page<Forum> routesEntity = forumRepository.findAllByType(thisType,pageable);
        Page<Forum> routesEntity = forumRepository.findByType(thisType,pageable);
        System.out.println(routesEntity);
            long count = routesEntity.getTotalElements();


            // Entity -> DTO
            Page<ForumDTO> routeDto = routesEntity.map(route -> {
                return ForumDTO.builder()
                        .forumId(route.getId())
                        .title(route.getTitle())
                        .content(route.getContent())
                        .createdAt(route.getCreatedAt())
                        .views(route.getViews())
                        .routeLocation(route.getRouteLocation())
                        .routeLocation(route.getRouteLocation())
                        .writerNickname(route.getWriter().getNickname())
                        .build();
            });

            // 결과 담아서 넘기는 맵
            Map<String, Object> result = new HashMap<>();
            result.put("res", routeDto); // 해당 페이지에 띄울 글 목록
            result.put("all", count); // 페이징을 위한 전체 데이터 개수

            return result;
    }



    // ReviewServiceImpl ------------------------------------------------------------------------------------
    @Override
    public List<ReviewDTO> getReviewsRv(Integer page, PageInfo pageInfo) throws Exception{
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");

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
        List<ReviewDTO> list = new ArrayList<>();
        return list;
    }

    @Override
    public ReviewDTO getReviewInfo(Long id) throws Exception {
        Optional<Forum> reviewInfo=forumRepository.findById(id);
        if(reviewInfo.isEmpty()) return null;
        Forum review=reviewInfo.get();

        int views=review.getViews();
        review.setViews(views+1);
        forumRepository.save(review);

        User writer=review.getWriter();

        // DTO 생성
        ReviewDTO getReview= ReviewDTO.builder()
                .forumId(review.getId())
                .forumType(review.getType())
                .title(review.getTitle())
                .content(review.getContent())
                .views(views+1)
                .isTemp(review.getIsTemporary())
                .createdAt(review.getCreatedAt())
//                .writerNickname(String.valueOf(userRepository.findById(userId).get()))
                .writerNickname(writer.getNickname())
//                .writerPic(String.valueOf(userRepository.findById(userId).get()))
                .build();
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
                .path("http://localhost:8080/imageview/"+filename)
                .fileName(filename)
                .build();

        forumFileRepository.save(reviewFileEntity);
        return "http://localhost:8080/imageview/"+filename;

    }



    @Override
    public void reviewWrite(Map<String, String> res, Long accompId, Boolean temp, Long userId) throws Exception {
        System.out.println("리뷰작성서비스");
        String title=res.get("title");
        String content=res.get("content");

        Forum reviewEntity=Forum.builder()
                .title(title)
                .content(content)
                .type("후기")
                .isTemporary(temp)
                .writer(userRepository.findById(userId).get())
                .thisAccompany(accompanyRepository.findById(accompId).get())
                .views(0)
                .build();

        forumRepository.save(reviewEntity);
    }

    @Override
    public void reviewModify(Map<String, String> res, Long id, Long userId) throws Exception{
        System.out.println("리뷰 수정 서비스");

        Forum review=forumRepository.findById(id).orElse(null);
        if(review==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

        review = Forum.builder()
                .id(id)
                .title(title)
                .content(content)
                .type("후기")
                .writer(userRepository.findById(userId).get())
                .isTemporary(false)
                .views(0)
                .build();
        forumRepository.save(review);
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
    public Map<String, Object> getMyShares(Long userId, Pageable pageable) {

        // 데이터 확보
        String thisType = "나눔";
        Page<Forum> mySharesEntity = forumRepository.findAllByUserIdAndType(userId, pageable, thisType);

        // 쿼리에 맞는 모든 데이터 확보 -> 전체 개수 확보 ( 전체 페이지 개수 위함 )
        List<Forum> allMyRoutes = forumRepository.findAllByUserIdAndType(userId, thisType);
        int count = allMyRoutes.size();

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

        // 결과 담아서 넘기는 맵
        Map<String, Object> result = new HashMap<>();
        result.put("res", mySharesDto); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;
    }


    // MyForum(ROUTE) ----------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMyRoutes(Long userId, Pageable pageable) {

        // 조건에 맞는 데이터 확보 ( 조건: 5개, 최신순 )
        String thisType = "경로";
        Page<Forum> myRoutesEntity = forumRepository.findAllByUserIdAndType(userId, pageable, thisType);

        // 쿼리에 맞는 모든 데이터 확보 -> 전체 개수 확보 ( 전체 페이지 개수 위함 )
        List<Forum> allMyRoutes = forumRepository.findAllByUserIdAndType(userId, thisType);
        int count = allMyRoutes.size();

        // Entity -> DTO
        Page<MyForumRouteResponseDto> myRouteDto = myRoutesEntity.map(route -> {
            return MyForumRouteResponseDto.builder()
                    .forumId(route.getId())
                    .title(route.getTitle())
                    .content(route.getContent())
                    .createdAt(route.getCreatedAt())
                    .views(route.getViews())
                    .location(route.getRouteLocation())
                    .build();
        });

        // 결과 담아서 넘기는 맵
        Map<String, Object> result = new HashMap<>();
        result.put("res", myRouteDto); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;
    }

    @Override
    public Boolean myForumDelete(Long forumId) throws Exception {
        forumRepository.deleteById(forumId);
        return true;
    }

    @Override
    public void shareStatus(Long forumId, String stat) throws Exception {
        System.out.println("forumId : "+forumId);
        System.out.println("stat : "+stat);

        Optional<Forum> forum=forumRepository.findById(forumId);
        Forum forum2= Forum.builder()
                .id(forumId)
                .content(forum.get().getContent())
                .writer(forum.get().getWriter())
                .isTemporary(forum.get().getIsTemporary())
                .title(forum.get().getTitle())
                .type(forum.get().getType())
                .views(forum.get().getViews())
                .status(stat)
                .build();
        forumRepository.save(forum2);
    }

}
