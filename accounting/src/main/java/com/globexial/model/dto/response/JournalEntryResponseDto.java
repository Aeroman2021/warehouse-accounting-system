package com.globexial.model.dto.response;

import com.globexial.model.enums.EntryStatus;

public record JournalEntryResponseDto(
        Long id,
        String referencedNumber,
        String description,
        EntryStatus entryStatus
) {
}
