package com.pickupluck.ecogging.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScrapDTO {
    private Long ForumId;

    @Builder
    public ScrapDTO(Long forumId){
        this.ForumId=forumId;
    }
}
