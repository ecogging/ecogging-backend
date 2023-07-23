package com.pickupluck.ecogging.domain.user.entity;


import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Corporate extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "corporate_id")
    private Long id;

    private String name;

    private String registerNumber;

    private String representative;

    @OneToOne(mappedBy = "corporate")
    private User manager;

    public void registerManager(User user) {
        this.manager = manager;
    }
}
