package com.pickupluck.ecogging.domain.message.entity;


import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA에서 관리할 수 있도록 -> 기본 생성자 필수( JPA가 entity 객체 생성할 때 기본 생성자 사용 )
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false)
public class MessageRoom {
    @Id
    @Column(name = "message_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JoinColumn(name = "user_id")
    private User initialSender;

    private User initialReceiver;
    private Long createdFrom;
}
