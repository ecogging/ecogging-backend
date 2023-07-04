package com.pickupluck.ecogging.domain.message.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 DB에 위임
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer read;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 업데이트
    @JoinColumn(name = "receiver_id") // User 테이블과 JOIN 해서 아이디 가져오기
    @OnDelete(action = OnDeleteAction.NO_ACTION) // User 제거되면 이것도 제거
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User sender;

    @Column(nullable = false, name = "deleted_by_receiver")
    private Boolean deletedByRcv;

    @Column(nullable = false, name = "deleted_by_sender")
    private Boolean deletedBySnd;

}
