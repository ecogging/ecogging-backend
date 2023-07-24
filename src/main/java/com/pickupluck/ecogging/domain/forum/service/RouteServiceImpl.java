package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.entity.Route;
import com.pickupluck.ecogging.domain.forum.repository.RouteRepository;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public List<ForumDTO> getRoutes(Integer page, PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, 5);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Route> pages=routeRepository.findAllByType("경로",pageRequest,sortByCreateAtDesc);
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
        for(Route route : pages.getContent()) {
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

        Route routeEntity=new Route();
        routeEntity.setTitle(title);
        routeEntity.setContent(content);
        routeEntity.setType("경로");
        routeEntity.setCreatedAt(LocalDateTime.now());
        routeEntity.setUserId(123);
        routeEntity.setRouteLocation(routeLocation);
//        routeEntity.setAccompanyId(1);
        routeRepository.save(routeEntity);
    }

    @Override
    public ForumDTO getRouteInfo(Long id) throws Exception {
        Optional<Route> routeInfo=routeRepository.findById(id);
        if(routeInfo.isEmpty()) return null;

        // 작성자 가져오기
        Route route=routeInfo.get();
        Optional<User> writerOpt = userRepository.findById(route.getUserId());
        User writer = writerOpt.get();

        // DTO 생성
        ForumDTO getRoute= ForumDTO.builder()
                        .forumId(route.getId())
                        .forumType(route.getType())
                        .title(route.getTitle())
                        .content(route.getContent())
                        .views(Integer.getInteger(route.getViews()+""))
                        .isTemp(route.getIsTemporary())
                        .routeLocation(route.getRouteLocation())
                        .routeLocationDetail(route.getRouteLocationDetail())
                        .writerId(writer.getId())
                        .writerNickname(writer.getNickname())
                        .writerPic(writer.getProfileImageUrl())
                        .build();


        route.setViews(route.getViews()+1);
        routeRepository.save(route);
//        return getRoute;
        return null;
    }
}
