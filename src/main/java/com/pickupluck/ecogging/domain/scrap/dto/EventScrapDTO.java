package com.pickupluck.ecogging.domain.scrap.dto;

import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventScrapDTO {
    private Integer scrapId;
    private Long userId;
    private Integer eventId;

    public EventScrapDTO(Eventscrap eventscrap) {
        this.scrapId = eventscrap.getScrapId();
        this.userId = eventscrap.getUserScrap().getId();
        this.eventId = eventscrap.getEventScrap().getEventId();
    }

    public EventScrapDTO(Integer scrapId, Long userId, Integer eventId) {
        this.scrapId = scrapId;
        this.userId = userId;
        this.eventId = eventId;
    }
}
