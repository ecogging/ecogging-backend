package com.pickupluck.ecogging.domain.scrap.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class ForumScrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="forum_id")
    private Forum forum;

    @Builder
    public ForumScrap(Long scrapId, User userId, Forum forum) {
        this.id=scrapId;
        this.userId=userId;
        this.forum=forum;
    }

}
