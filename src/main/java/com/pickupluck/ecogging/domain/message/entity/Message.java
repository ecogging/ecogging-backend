package com.pickupluck.ecogging.domain.message.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false) // equals, hashCode 메서드 자동 생성 -> 내용, 객체 동등성/동일성 비교
public class Message extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 DB에 위임 auto_increment
    @Column(name = "message_id")
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_room_id", referencedColumnName = "message_room_id")
    private MessageRoom messageRoom; // FK

    @Column(columnDefinition = "TEXT", nullable = false, length = 300) // 내용 길이 300자 제한 -- 프론트에서 필터
    private String content;

    @Column(nullable = false, name = "raed_YN")
    private Integer read; // 읽음 여부 상태

    @ManyToOne(fetch = FetchType.LAZY) // 한 명 : 여러 개 쪽지(기준:Many)
    @JoinColumn(name = "receiver_id") // User 테이블과 JOIN 해서 아이디 가져오기
    @OnDelete(action = OnDeleteAction.NO_ACTION) // User 제거되면 이것도 제거
    private User receiver; // FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User sender; // FK

    @Column(nullable = false, name = "deleted_by_receiver_YN")
    private Integer deletedByRcv;

    @Column(nullable = false, name = "deleted_by_sender_YN")
    private Integer deletedBySnd;

    @Builder
    public Message(Long id, MessageRoom messageRoom, String content, Integer read, User receiver, User sender, Integer deletedByRcv, Integer deletedBySnd) {
        this.id = id;
        this.messageRoom = messageRoom;
        this.content = content;
        this.read = read;
        this.receiver = receiver;
        this.sender = sender;
        this.deletedByRcv = deletedByRcv;
        this.deletedBySnd = deletedBySnd;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageRoom=" + messageRoom +
                ", content='" + content + '\'' +
                ", read=" + read +
                ", receiver=" + receiver +
                ", sender=" + sender +
                ", deletedByRcv=" + deletedByRcv +
                ", deletedBySnd=" + deletedBySnd +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
