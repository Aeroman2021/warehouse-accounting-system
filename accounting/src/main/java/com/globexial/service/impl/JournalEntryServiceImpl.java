package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.event.StockMovementCreatedEvent;
import com.globexial.repository.JournalEntryRepository;
import com.globexial.service.JournalEntryService;
import com.globexial.service.factory.JournalProcessFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
