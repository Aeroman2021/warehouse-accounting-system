package com.globexial.model.dto.request;



import java.math.BigDecimal;

public record JournalItemRequestDto(
        Long accountId,
        Long journalEntryId,
        BigDecimal debit,
        BigDecimal credit,
        String description
) {
}
