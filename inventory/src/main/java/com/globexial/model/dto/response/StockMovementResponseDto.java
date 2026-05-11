package com.globexial.model.dto.response;

import com.globexial.model.entity.Party;
import com.globexial.model.entity.Stock;
import com.globexial.model.enums.MovementType;

import java.math.BigDecimal;

public record StockMovementResponseDto(
        Long id,
        Stock stock,
        int quantity,
        BigDecimal totalPrice,
        Party party,
        MovementType type
) {
}
