package com.globexial.model.entity;


import com.globexial.model.enums.EntryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String referenceNumber;
    private String description;

    @Enumerated(EnumType.STRING)
    private EntryStatus entryStatus;

    @OneToMany(mappedBy = "journalEntry",cascade = CascadeType.ALL)
    private List<JournalItem> journalItemList;

    private LocalDateTime createdAt;

}
