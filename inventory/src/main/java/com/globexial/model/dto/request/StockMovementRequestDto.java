package com.globexial.model.dto.request;


import com.globexial.model.enums.MovementType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StockMovementRequestDto(
        @NotNull(message = "Stock id must mot be null.")
        Long stockId,

        @NotNull
        @Positive(message = "Quantity must be a positive number")
        Integer quantity,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal unitPrice,

        @NotNull(message = "Party id must mot be null.")
        Long partyId,

        @NotNull(message = "Movement type must mot be null.")
        MovementType movementType,

        @NotBlank(message = "description is required")
        String description
) {


}
