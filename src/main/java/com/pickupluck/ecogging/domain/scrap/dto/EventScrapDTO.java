package com.pickupluck.ecogging.domain.scrap.dto;

import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventScrapDTO {
    private Long scrapId;
    private Long userId;
    private Integer eventId;

    public EventScrapDTO(Eventscrap eventscrap) {
        this.scrapId = eventscrap.getScrapId();
        this.userId = eventscrap.getUser().getId();
        this.eventId = eventscrap.getEvent().getEventId();
    }

    public EventScrapDTO(Long scrapId, Long userId, Integer eventId) {
        this.scrapId = scrapId;
        this.userId = userId;
        this.eventId = eventId;
    }
}
