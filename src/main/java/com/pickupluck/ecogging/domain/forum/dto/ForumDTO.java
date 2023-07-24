package com.pickupluck.ecogging.domain.forum.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.Route;
import com.pickupluck.ecogging.domain.forum.entity.Share;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumDTO {

    private long id;
    private String type;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long views;
    private Integer fileId;
    private boolean isTemporary;
    private long userId;
    private String routeLoc;
    private String routeLocDetail;
    private Integer accompanyId;

    public ForumDTO(Share share) {
        this.id = share.getId();
        this.type = share.getType();
        this.title = share.getTitle();
        this.content = share.getContent();
        this.createdAt = share.getCreatedAt();
        this.views = share.getViews();
        this.fileId = share.getFileId();
        this.isTemporary = share.getIsTemporary();
        this.userId = share.getUserId();
        this.routeLoc = share.getRouteLocation();
        this.routeLocDetail = share.getRouteLocationDetail();
        this.accompanyId = share.getAccompanyId();
    }

    public ForumDTO(Route route) {
        this.id = route.getId();
        this.type = route.getType();
        this.title = route.getTitle();
        this.content = route.getContent();
        this.createdAt = route.getCreatedAt();
        this.views = route.getViews();
        this.fileId = route.getFileId();
        this.isTemporary = route.getIsTemporary();
        this.userId = route.getUserId();
        this.routeLoc = route.getRouteLocation();
        this.routeLocDetail = route.getRouteLocationDetail();
        this.accompanyId = route.getAccompanyId();
    }
}
