package com.pickupluck.ecogging.domain.message.entity;

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
public class MessageContent extends BaseEntity {
    @Id
    @Column(name = "message_id", nullable = false)
    private Long id; // Message Entity PK 참조

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // @id로 지정한 컬럼에 @OneToOne or @ManyToOne 관계 매핑
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(columnDefinition = "TEXT", nullable = false, length = 300) // 내용 길이 300자 제한
    private String content;

    
    @Builder
    public MessageContent(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
