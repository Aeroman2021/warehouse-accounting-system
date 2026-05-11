package com.globexial.model.dto.response;

import com.globexial.model.enums.PartyRole;
import com.globexial.model.enums.PartyType;

public record PartyResponseDto(

        Long id,

        String nationalCode,

        String economicCode,

        PartyType partyType,

        PartyRole partyRole,
        Long parentId
) {
}
