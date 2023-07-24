package com.pickupluck.ecogging.domain.forum.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.Share;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumDTO extends BaseEntity {

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
    
}
