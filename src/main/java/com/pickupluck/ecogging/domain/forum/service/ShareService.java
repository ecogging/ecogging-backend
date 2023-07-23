package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ShareService {
    List<ForumDTO> getShares(Integer page, PageInfo pageInfo) throws Exception;

    void shareWrite(Map<String, String> res) throws Exception;

    String shareImgUpload(MultipartFile file) throws Exception;

    ForumDTO getShareInfo(Long id) throws Exception;

    void shareDel(long id) throws Exception;

    void shareModify(Map<String, String> res, long id) throws Exception;
}
