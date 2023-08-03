package com.pickupluck.ecogging.domain.scrap.service;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.domain.scrap.entity.ForumScrap;
import com.pickupluck.ecogging.domain.scrap.repository.ForumscrapRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForumScrapServiceImpl implements ForumScrapService{

    @Autowired
    private ForumscrapRepository forumscrapRepository;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean setForumScrap(Long forumId, Long userId) throws Exception {
        System.out.println("스크랩 서비스");
        Optional<ForumScrap> oscrap = forumscrapRepository.findByForumIdAndUserId(forumId, userId);

        if(oscrap.isEmpty()) {
            System.out.println("디비에 스크랩 없음");
            Forum forum = forumRepository.findById(forumId).get();
            User user=userRepository.findById(userId).get();
            ForumScrap forumscrap = ForumScrap.builder()
                    .forum(forum)
                    .user(user)
                    .build();
            forumscrapRepository.save(forumscrap);
            return true;
        } else {
            forumscrapRepository.delete(oscrap.get());
            return false;
        }
    }

    @Override
    public Boolean isForumScrap(Long forumId, Long userId) throws Exception {
        Optional<ForumScrap> oscrap = forumscrapRepository.findByForumIdAndUserId(forumId, userId);
        if(oscrap.isEmpty()) return false;

        return true;
    }

}
