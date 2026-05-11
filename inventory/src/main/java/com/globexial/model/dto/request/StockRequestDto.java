package com.globexial.model.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StockRequestDto(
        @Positive
        Integer quantity,

        @NotNull
        Long productId,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal avgCost,

        @NotNull
        Long wareHouseId
) {


}
