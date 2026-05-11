package com.globexial.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WareHouseRequestDto(
        @NotBlank(message = "Warehouse name is required")
        String name
) {
}
