package com.globexial.model.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record JournalItemResponseDto(
        Long id,
        Long accountId,
        Long journalEntryId,
        BigDecimal debit,
        BigDecimal credit,
        String description
) {
}
