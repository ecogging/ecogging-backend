package com.pickupluck.ecogging.domain.message.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

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

    @Column(name = "visible_to_msg", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibilityState visibilityTo; // 삭제 상태 추가

    @Builder
    public Message(Long id, MessageRoom messageRoom, String content, Integer read, User receiver, User sender) {
        Assert.notNull(messageRoom, "messageRoom null 불가능");
        Assert.notNull(receiver, "receiver null 불가능");
        Assert.notNull(sender, "sender null 불가능");
        validateContent(content);
        this.id = id;
        this.messageRoom = messageRoom;
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
        this.read = read;
        this.visibilityTo = VisibilityState.BOTH; // 삭제 상태 추가
    }

    public void changeVisibilityTo(VisibilityState visibilityTo) {
        if (this.visibilityTo.equals(VisibilityState.BOTH)) {
            this.visibilityTo = visibilityTo;
        } else if (this.visibilityTo.equals(VisibilityState.ONLY_INITIAL_RECEIVER)) {
            if (visibilityTo.equals(VisibilityState.ONLY_INITIAL_SENDER)) {
                this.visibilityTo = VisibilityState.NO_ONE;
            }
        } else if (this.visibilityTo.equals(VisibilityState.ONLY_INITIAL_SENDER)) {
            if (visibilityTo.equals(VisibilityState.ONLY_INITIAL_RECEIVER)) {
                this.visibilityTo = VisibilityState.NO_ONE;
            }
        }
    }

    private void validateContent(String content) {
        Assert.notNull(content, "메세지 내용은 비어있을 수 없습니다.");
        Assert.isTrue(content.length() <= 300,
                "메세지 길이는 300자 이하");
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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
