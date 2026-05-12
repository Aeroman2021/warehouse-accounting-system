package com.globexial.model.event;

import com.globexial.model.enums.MovementType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record StockMovementCreatedEvent(
        Long stockMovementId,
        Long stockId,
        Long partyId,
        MovementType movementType,
        Integer quantity,
        BigDecimal totalPrice,
        BigDecimal avgCost,
        Timestamp createdAt
) {
}
