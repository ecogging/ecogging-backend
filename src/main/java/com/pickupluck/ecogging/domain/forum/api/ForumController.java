package com.pickupluck.ecogging.domain.forum.api;
import com.pickupluck.ecogging.domain.forum.service.ForumService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ForumController {

    @Autowired
    private ForumService forumService;

}
