package com.globexial.model.dto.request;



import com.globexial.model.entity.JournalItem;
import com.globexial.model.enums.EntryStatus;

import java.util.List;

public record JournalEntryRequestDto(
        String referencedNumber,
        String description,
        EntryStatus entryStatus,
        List<JournalItem> journalItemList
) {
}
