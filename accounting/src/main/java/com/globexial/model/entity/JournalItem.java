package com.globexial.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "journal_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JournalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountId;

    @ManyToOne
    @JoinColumn(name = "journal_entry_id")
    private JournalEntry journalEntryId;

    private BigDecimal debit;
    private BigDecimal credit;
    private String description;
}
