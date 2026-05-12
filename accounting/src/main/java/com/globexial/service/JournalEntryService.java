package com.globexial.service;

import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.event.StockMovementCreatedEvent;
import com.globexial.service.base.JournalEntryBaseCrud;

public interface JournalEntryService extends JournalEntryBaseCrud<JournalEntryResponseDto,StockMovementCreatedEvent> {

}
