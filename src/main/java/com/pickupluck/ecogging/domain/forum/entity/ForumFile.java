package com.pickupluck.ecogging.domain.forum.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ForumFile extends BaseEntity {

    @Id
    @Column(name = "forum_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 첨부파일 ID

    @Column(nullable = false)
    private String fileName; // 첨부파일 이름

    @Column(nullable = false)
    private String path; // 첨부파일 경로

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum; // Forum 테이블과 조인 -> 첨부파일이 포함된 포럼글 ID

    @Builder
    public ForumFile(Long id, String fileName, String path, Forum forum) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
        this.forum = forum;
    }
}
