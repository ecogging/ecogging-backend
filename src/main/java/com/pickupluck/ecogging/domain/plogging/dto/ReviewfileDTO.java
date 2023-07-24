package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewfileDTO extends BaseEntity {
    private Long id;
    private String fileName;
    private LocalDateTime createdAt;
    private String path;


}
