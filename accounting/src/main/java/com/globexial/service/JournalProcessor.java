package com.globexial.service;

import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.enums.MovementType;
import com.globexial.model.event.StockMovementCreatedEvent;

public interface JournalProcessor {
    MovementType getType();
    JournalEntryResponseDto process(StockMovementCreatedEvent event);
}
