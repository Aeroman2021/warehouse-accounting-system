package com.globexial.model.dto.request;

import com.globexial.model.enums.PartyRole;
import com.globexial.model.enums.PartyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PartyRequestDto(

        @Size(max = 11,message = "Nationalised must be valid.")
        String nationalCode,

        String economicCode,

        @NotNull(message = "PartyType is required")
        PartyType partyType,

        @NotNull(message = "PartyRole is required")
        PartyRole partyRole,

        @NotNull
        Long parentId

) {
}
