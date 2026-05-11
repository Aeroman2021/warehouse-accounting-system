package com.globexial.model.entity;

import com.globexial.model.enums.PartyRole;
import com.globexial.model.enums.PartyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parties")
@Getter @Setter
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationalCode;

    private String economicCode;

    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    @Enumerated(EnumType.STRING)
    private PartyRole partyRole;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Party parent;

    public Party(Long id, String nationalCode, String economicCode,
                 PartyType partyType, PartyRole partyRole, Party parent) {
        this.id = id;
        this.nationalCode = nationalCode;
        this.economicCode = economicCode;
        this.partyType = partyType;
        this.partyRole = partyRole;
        this.parent = parent;
    }

    public Party() {
    }
}
