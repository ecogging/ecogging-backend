package com.pickupluck.ecogging.domain.message.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

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
    
    @Column(name = "msg_read_by", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadState readBy; // 읽음 상태 추가

    @Builder
    public Message(Long id, MessageRoom messageRoom, String content, User receiver, User sender) {
        Assert.notNull(messageRoom, "messageRoom null 불가능");
        Assert.notNull(receiver, "receiver null 불가능");
        Assert.notNull(sender, "sender null 불가능");
        validateContent(content);
        this.id = id;
        this.messageRoom = messageRoom;
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
        this.visibilityTo = VisibilityState.BOTH; // 삭제 상태 추가

        // visibilityTo와 readBy 값 설정
        ReadState readBy = messageRoom.getInitialSender().equals(sender)
                ? ReadState.ONLY_INITIAL_SENDER : ReadState.ONLY_INITIAL_RECEIVER;
        this.readBy = readBy;
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

    // @PrePersist 어노테이션을 사용한 메서드 추가
    @PrePersist
    public void synchronizeReadBy() {
        if (this.messageRoom != null) {
            this.messageRoom.setReadByFromMessage(this.readBy);
        }
    }

    public void changeReadBy(ReadState readBy) {
        if (this.readBy.equals(ReadState.NO_ONE)) { // 모두 안읽었으면
            this.readBy = readBy; // 매개변수 들어오는 것대로 값 변경
        } else if (this.readBy.equals(ReadState.ONLY_INITIAL_RECEIVER)) {
            if(readBy.equals(ReadState.ONLY_INITIAL_SENDER)) {
                this.readBy = ReadState.BOTH;
            }
        } else if (this.readBy.equals(ReadState.ONLY_INITIAL_SENDER)) {
            if(readBy.equals(ReadState.ONLY_INITIAL_RECEIVER)) {
                this.readBy = ReadState.BOTH;
            }
        }
        // 이미 한 명이 읽었는데 다른 쪽도 읽으면 모두 읽음으로 변경
    }



    private void validateContent(String content) {
        Assert.notNull(content, "메세지 내용은 비어있을 수 없습니다.");
        Assert.isTrue(content.length() <= 300,
                "메세지 길이는 300자 이하");
    }

}
