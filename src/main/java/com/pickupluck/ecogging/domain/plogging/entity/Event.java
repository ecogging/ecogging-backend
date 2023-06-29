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
public class Event {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer eventId ;

    @Column
    private String eventTitle;
    @Column
    private String eventContent;
    @Column
    private Date eventDay;
    @Column
    private  Date createdAt;
    @Column
    private Boolean activate;
    @Column
    private Integer views;
    @Column
    private Integer save;
    @Column
    private Integer userId;

    @Override
    public String toString() {
        return String.format("[%d,%s,%s,%tF,%tF,%b,%d,%d,%d]", eventId,eventTitle,eventContent,eventDay,createdAt,activate,views,save,userId);
    }
}
