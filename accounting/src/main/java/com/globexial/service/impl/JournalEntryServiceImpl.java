package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.JournalEntryRequestDto;
import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.entity.Account;
import com.globexial.model.entity.JournalEntry;
import com.globexial.model.entity.JournalItem;
import com.globexial.model.enums.AccountType;
import com.globexial.model.enums.EntryStatus;
import com.globexial.model.event.StockMovementCreatedEvent;
import com.globexial.repository.AccountRepository;
import com.globexial.repository.JournalEntryRepository;
import com.globexial.service.JournalEntryService;
import com.globexial.service.JournalProcessFactory;
import com.globexial.service.SaleJournalProcessor;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JournalEntryServiceImpl implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final JournalProcessFactory journalProcessFactory;

    @Override
    public JournalEntryResponseDto process(StockMovementCreatedEvent event) {
        var processor = journalProcessFactory.getProcessor(event.movementType());
        return processor.process(event);
    }


    @Override
    public JournalEntryResponseDto save(JournalEntryRequestDto journalEntryRequestDto) {
        var journalEntry = JournalEntry.builder()
                .referenceNumber(journalEntryRequestDto.referencedNumber())
                .journalItemList(journalEntryRequestDto.journalItemList())
                .entryStatus(journalEntryRequestDto.entryStatus())
                .description(journalEntryRequestDto.description())
                .createdAt(LocalDateTime.now())
                .build();

        var savedJournalEntry = journalEntryRepository.save(journalEntry);

        return new JournalEntryResponseDto(savedJournalEntry.getId(),
                savedJournalEntry.getReferenceNumber(),
                savedJournalEntry.getDescription(),
                savedJournalEntry.getEntryStatus());
    }

    @Override
    public JournalEntryResponseDto findById(Long id) {
        var journalEntry = journalEntryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("journal entry",id)));

        return new JournalEntryResponseDto(journalEntry.getId(),
                journalEntry.getReferenceNumber(),
                journalEntry.getDescription(),
                journalEntry.getEntryStatus());
    }

    @Override
    public Page<JournalEntryResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var JournalEntries =
                journalEntryRepository.findAll(pageable)
                        .stream()
                        .map(journalEntry -> new JournalEntryResponseDto(journalEntry.getId(),
                                journalEntry.getReferenceNumber(),
                                journalEntry.getDescription(),
                                journalEntry.getEntryStatus()))
                        .toList();

        return new PageImpl<>(JournalEntries, pageable, JournalEntries.size());
    }





}
