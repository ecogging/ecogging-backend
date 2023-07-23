package com.pickupluck.ecogging.domain.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sharefile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "file_id")
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column
    private String path;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
