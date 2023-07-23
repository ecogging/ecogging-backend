package com.pickupluck.ecogging.domain.user.entity;


import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.dto.CorporateProfileModifyRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Corporate extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "corporate_id")
    private Long id;

    private String corpName;

    private String corpRegisterNumber;

    private String corpRepresentative;

    @OneToOne(mappedBy = "corporate")
    private User manager;

    @Builder
    public Corporate(String corpName, String corpRegisterNumber, String corpRepresentative) {
        this.corpName = corpName;
        this.corpRegisterNumber = corpRegisterNumber;
        this.corpRepresentative = corpRepresentative;
    }

    public void registerManager(User user) {
        this.manager = manager;
    }

    public void modifyInformation(CorporateProfileModifyRequest request) {
        this.corpName = request.getCorpName();
        this.corpRegisterNumber = request.getCorpRegisterNumber();
        this.corpRepresentative = request.getCorpRepresentative();
    }
}
