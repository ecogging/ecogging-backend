package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.entity.Share;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO extends BaseEntity {

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

    public ReviewDTO(Review review){
        this.id=review.getId();
        this.type=review.getType();
        this.title=review.getTitle();
        this.content=review.getContent();
        this.createdAt=review.getCreatedAt();
        this.views=review.getViews();
        this.fileId=review.getFileId();
        this.isTemporary=review.getIsTemporary();
        this.userId=review.getUserId();
        this.routeLoc=review.getRouteLocation();
        this.routeLocDetail=review.getRouteLocationDetail();
        this.accompanyId=review.getAccompanyId();
    }


//    public ReviewDTO(Review review) {
//        this.forumId = review.getId();
//        this.type = review.getType();
//        this.title = review.getTitle();
//        this.content = review.getContent();
//        this.createdAt = review.getCreatedAt();
//        this.views = review.getViews();
//        this.fileId = Math.toIntExact(review.getImageFile() != null ? review.getImageFile().getId() : null);
//        //this.isTemporary = review.getIsTemporary();
//        this.userId = review.getUser().getId();
//        // Review 클래스에 해당하는 필드들도 추가적으로 매핑해야 함
//        // this.routeLoc = review.getRouteLoc();
//        // this.routeLocDetail = review.getRouteLocDetail();
//        // this.accompanyId = review.getAccompany().getId();
//    }
}
