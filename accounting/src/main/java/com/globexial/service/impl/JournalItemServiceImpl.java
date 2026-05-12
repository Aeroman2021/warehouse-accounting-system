package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.JournalItemRequestDto;
import com.globexial.model.dto.response.JournalItemResponseDto;
import com.globexial.model.entity.JournalItem;
import com.globexial.repository.AccountRepository;
import com.globexial.repository.JournalEntryRepository;
import com.globexial.repository.JournalItemRepository;
import com.globexial.service.JournalItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JournalItemServiceImpl implements JournalItemService {

    private final JournalItemRepository journalItemRepository;
    private final AccountRepository accountRepository;
    private final JournalEntryRepository journalEntryRepository;

    @Override
    public JournalItemResponseDto save(JournalItemRequestDto journalItemRequestDto) {
        var account = accountRepository.findById(journalItemRequestDto.accountId()).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("account",
                        journalItemRequestDto.accountId())));

        var journalEntry = journalEntryRepository.findById(journalItemRequestDto.accountId()).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("journal entry",
                        journalItemRequestDto.accountId())));

        var savedJournalItem = JournalItem.builder()
                .accountId(account)
                .journalEntry(journalEntry)
                .debit(journalItemRequestDto.debit())
                .credit(journalItemRequestDto.credit())
                .description(journalItemRequestDto.description())
                .build();

        return new JournalItemResponseDto(savedJournalItem.getId(), account.getId(),
                journalEntry.getId(), savedJournalItem.getDebit(), savedJournalItem.getCredit(),
                savedJournalItem.getDescription());

    }

    @Override
    public JournalItemResponseDto findById(Long id) {
        var journalItem = journalItemRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("journal item",id)));

        return new JournalItemResponseDto(journalItem.getId(), journalItem.getAccountId().getId(),
                journalItem.getJournalEntry().getId(), journalItem.getDebit(), journalItem.getCredit(),
                journalItem.getDescription());
    }

    @Override
    public Page<JournalItemResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var JournalItems =
                journalItemRepository.findAll(pageable)
                        .stream()
                        .map(journalItem -> new JournalItemResponseDto(
                                journalItem.getId(),
                                journalItem.getAccountId().getId(),
                                journalItem.getJournalEntry().getId(),
                                journalItem.getDebit(),
                                journalItem.getCredit(),
                                journalItem.getDescription()))
                        .toList();

        return new PageImpl<>(JournalItems, pageable, JournalItems.size());
    }
}

