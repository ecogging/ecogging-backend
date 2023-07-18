package com.pickupluck.ecogging.domain.message.entity;


import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "initial_sender")
    private User initialSender;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "initial_receiver")
    private User initialReceiver;

    @Builder
    public MessageRoom(Long id, User initialSender, User initialReceiver) {
        this.id = id;
        this.initialSender = initialSender;
        this.initialReceiver = initialReceiver;
    }

    @Override
    public String toString() {
        return "MessageRoom{" +
                "id=" + id +
                ", initialSender=" + initialSender +
                ", initialReceiver=" + initialReceiver +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
