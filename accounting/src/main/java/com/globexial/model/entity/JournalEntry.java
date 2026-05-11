package com.globexial.model.entity;


import com.globexial.model.enums.EntryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "journal_entries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referencedNumber;
    private String description;

    @Enumerated(EnumType.STRING)
    private EntryStatus entryStatus;

    @OneToMany(mappedBy = "journalEntryId")
    private List<JournalItem> journalItemList;

    private LocalDateTime createdAt;
}
