package com.globexial.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProductRequestDto(
        @NotBlank(message = "Product name is requited")
         String name,

        @NotBlank
        @Pattern(
                regexp = "^[A-Z0-9]+$",
                message = "SKU format is invalid."
        )
        String sku
) {}
