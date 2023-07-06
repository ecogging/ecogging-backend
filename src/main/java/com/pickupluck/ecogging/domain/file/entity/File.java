package com.pickupluck.ecogging.domain.file.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Long id;

    @NotBlank
    private String originName;

    @NotBlank
    private String fullPath;

    private Long size;

//    @OneToMany(mappedBy = "file")
//    private List<Event> events;

    public File(String originName, String fullPath, Long size) {
        this.originName = originName;
        this.fullPath = fullPath;
        this.size = size;
    }
}
