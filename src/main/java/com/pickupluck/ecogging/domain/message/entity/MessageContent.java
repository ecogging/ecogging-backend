package com.pickupluck.ecogging.domain.message.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class MessageContent extends BaseEntity {
    @Id
    @JoinColumn(name = "message_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
