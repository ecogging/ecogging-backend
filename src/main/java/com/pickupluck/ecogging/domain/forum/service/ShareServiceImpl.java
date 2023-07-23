package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.entity.Share;
import com.pickupluck.ecogging.domain.forum.entity.Sharefile;
import com.pickupluck.ecogging.domain.forum.repository.ShareFileRepository;
import com.pickupluck.ecogging.domain.forum.repository.ShareRepository;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
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
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final ShareFileRepository shareFileRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ForumDTO> getShares(Integer page, PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(page - 1, 5);

        //Page<Event> pages = eventRepository.findBySaveFalse(pageRequest);

        Sort sortByCreateAtDesc=Sort.by(Sort.Direction.DESC,"createdAt");
        Page<Share> pages = shareRepository.findAllByType("나눔",pageRequest,sortByCreateAtDesc);

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
        for (Share share : pages.getContent()) {
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

        Sharefile shareFileEntity=new Sharefile();
        shareFileEntity.setPath("C:/JSR/front-work/shareUpload/"+filename);
        shareFileEntity.setFileName(filename);
        shareFileEntity.setCreatedAt(LocalDateTime.now());
        shareFileRepository.save(shareFileEntity);
        return "C:/JSR/front-work/upload/"+filename;
    }

    @Override
    public ForumDTO getShareInfo(Long id) throws Exception {
        Optional<Share> shareInfo=shareRepository.findById(id);
        if(shareInfo.isEmpty()) return null;
        Share share=shareInfo.get();
        ForumDTO getShare=new ForumDTO(share);

        share.setViews(share.getViews()+1);
        shareRepository.save(share);
        return getShare;
    }

    @Override
    public void shareDel(long id) throws Exception {
        System.out.println("리뷰 삭제 서비스");
        shareRepository.deleteById(id);
    }

    @Override
    public void shareModify(Map<String, String> res, long id) throws Exception {
        System.out.println("리뷰 수정 서비스");

        Share share=shareRepository.findById(id).orElse(null);
        if(share==null){
            throw new EntityNotFoundException("해당 리뷰를 찾을 수 없음");
        }

        String title=res.get("title");
        String content=res.get("content");

//        Review reviewEntity=new Review();
        share.setTitle(title);
        share.setContent(content);
        share.setType("나눔");
        share.setCreatedAt(LocalDateTime.now());
        share.setUserId(123);
//        share.setAccompanyId(1);
        shareRepository.save(share);
    }

    @Override
    public void shareWrite(Map<String, String> res) throws Exception {
        System.out.println("나눔작성서비스");

        String title=res.get("title");
        String content=res.get("content");

        Share shareEntity=new Share();
        shareEntity.setTitle(title);
        shareEntity.setContent(content);
        shareEntity.setType("나눔");
        shareEntity.setCreatedAt(LocalDateTime.now());
        shareEntity.setUserId(123);
//        shareEntity.setAccompanyId();
        shareRepository.save(shareEntity);
    }



}
