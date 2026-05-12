package com.globexial.service.factory;

import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.entity.JournalEntry;
import com.globexial.model.entity.JournalItem;
import com.globexial.model.enums.EntryStatus;
import com.globexial.model.enums.MovementType;
import com.globexial.model.event.StockMovementCreatedEvent;
import com.globexial.repository.AccountRepository;
import com.globexial.repository.JournalEntryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaleJournalProcessor implements JournalProcessor{

    private final JournalEntryRepository journalEntryRepository;
    private final AccountRepository accountRepository;


    @Override
    public MovementType getType() {
        return MovementType.SALE;
    }

    @Override
    public JournalEntryResponseDto process(StockMovementCreatedEvent event) {
        List<JournalItem> journalItems = createJournalItemsFromForSale(event);

        var journalEntry = JournalEntry.builder()
                .referenceNumber(UUID.randomUUID().toString().replace("-", "")
                        .substring(0, 10))
                .entryStatus(EntryStatus.POSTED)
                .createdAt(LocalDateTime.now())
                .build();

        journalItems.forEach(e->e.setJournalEntry(journalEntry));
        journalEntry.setJournalItemList(journalItems);

        var savedJournalEntry = journalEntryRepository.save(journalEntry);

        return new JournalEntryResponseDto(savedJournalEntry.getId(),
                savedJournalEntry.getReferenceNumber(),
                savedJournalEntry.getDescription(),
                savedJournalEntry.getEntryStatus());
    }

    private @NonNull List<JournalItem> createJournalItemsFromForSale(
            StockMovementCreatedEvent event) {

        var saleRev = accountRepository.findByCode("4100").orElseThrow();
        var salesRevJournalItem = new JournalItem();
        salesRevJournalItem.setAccountId(saleRev);
        salesRevJournalItem.setDebit(BigDecimal.ZERO);
        salesRevJournalItem.setCredit(event.totalPrice());

        var accountRec = accountRepository.findByCode("1200").orElseThrow();
        var accountRecJournalItem = new JournalItem();
        accountRecJournalItem.setAccountId(accountRec);
        accountRecJournalItem.setDebit(event.totalPrice());
        accountRecJournalItem.setCredit(BigDecimal.ZERO);

        var COGS = accountRepository.findByCode("5100").orElseThrow();
        var COGSJournalItem = new JournalItem();
        COGSJournalItem.setAccountId(COGS);
        COGSJournalItem.setDebit(event.avgCost());
        COGSJournalItem.setCredit(BigDecimal.ZERO);

        var inventoryAsset = accountRepository.findByCode("1300").orElseThrow();
        var inventoryAssetJournalItem = new JournalItem();
        inventoryAssetJournalItem.setAccountId(inventoryAsset);
        inventoryAssetJournalItem.setDebit(BigDecimal.ZERO);
        inventoryAssetJournalItem.setCredit(event.avgCost());

        return List.of(
                salesRevJournalItem,
                accountRecJournalItem,
                COGSJournalItem,
                inventoryAssetJournalItem);
    }


}
