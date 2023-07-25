package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private String type;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long views;
    private Long fileId;
    private Boolean isTemporary;
    private Long userId;
    private String routeLoc;
    private String routeLocDetail;
    private Long accompanyId;

    public ReviewDTO(Forum review){
        this.id=review.getId();
        this.type=review.getType();
        this.title=review.getTitle();
        this.content=review.getContent();
        this.createdAt=review.getCreatedAt();
        this.views=Long.parseLong(review.getViews()+"");
        this.fileId=review.getFileId();
        this.isTemporary=review.getIsTemporary();
        this.userId=review.getWriter().getId();
        this.routeLoc=review.getRouteLocation();
        this.routeLocDetail=review.getRouteLocationDetail();
        this.accompanyId=review.getThisAccompany().getId();
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
