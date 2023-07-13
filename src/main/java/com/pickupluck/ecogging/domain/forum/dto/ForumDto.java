package com.pickupluck.ecogging.domain.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumDto {

    //private Integer num;

    private long forumId;
    private String type;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private long views;
    private  Integer fileId;
    private boolean tempId;
    private long userId;
    private String routeLoc;
    private String routeLocDetail;
    private Integer accompanyId;
}
