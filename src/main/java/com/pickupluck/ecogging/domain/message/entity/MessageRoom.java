package com.pickupluck.ecogging.domain.message.entity;


import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA에서 관리할 수 있도록 -> 기본 생성자 필수( JPA가 entity 객체 생성할 때 기본 생성자 사용 )
@Getter
@NoArgsConstructor // 기본 생성자
@EqualsAndHashCode(of="id", callSuper = false)
public class MessageRoom extends BaseEntity {
    @Id // PK에 매핑
    @Column(name = "message_room_id") // 'message_room_id' 이름 column에 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment에 위임
    private Long id; // message_room table의 PK

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩, Rooms N : User 1
    @JoinColumn(name = "initial_sender_id")
    private User initialSender;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "initial_receiver_id")
    private User initialReceiver;

    // * mappedBy 두 객체 중 하나의 객체만 테이블을 관리 가능하도록 설정
    // mappedBy가 적용된 객체는 조회만 가능 --> mappedBy가 정의되지 않은 객체가 Owner
    // 일반적으로 FK를 가진 객체를 Owner로 정의하는 것이 바람직
    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "visible_to", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisibilityState visibilityTo;

    @Column(name = "read_by", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadState readBy; // 읽음 상태 추가

    @Builder
    public MessageRoom(Long id, User initialSender, User initialReceiver) {
        this.id = id;
        this.initialSender = initialSender;
        this.initialReceiver = initialReceiver;
        this.visibilityTo = VisibilityState.BOTH;
        this.readBy = ReadState.ONLY_INITIAL_SENDER;
        this.messages = new ArrayList<>(); // messages 필드 초기화
    }

    public void setReadByFromMessage(ReadState readBy) {
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

    public void setVisibilityTo(VisibilityState visibilityTo) {
        this.visibilityTo = visibilityTo;
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


}
