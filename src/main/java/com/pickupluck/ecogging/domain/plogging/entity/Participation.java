package com.pickupluck.ecogging.domain.plogging.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer participationId ;

    @Column
    private Integer userId;
    @Column
    private Integer accompanyId;
    @Column
    private Integer eventId;
    @Column
    private Integer type;
    @Column
    private Boolean confirm;


    @Override
    public String toString() {
        return String.format("[%d,%d,%d,%d,%d,%b]", participationId,userId,accompanyId,eventId,type,confirm);
    }
}
