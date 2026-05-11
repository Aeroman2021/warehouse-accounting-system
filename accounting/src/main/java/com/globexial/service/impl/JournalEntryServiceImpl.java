package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.JournalEntryRequestDto;
import com.globexial.model.dto.response.JournalEntryResponseDto;
import com.globexial.model.entity.JournalEntry;
import com.globexial.repository.JournalEntryRepository;
import com.globexial.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JournalEntryServiceImpl implements JournalEntryService {

    private JournalEntryRepository journalEntryRepository;


    @Override
    public JournalEntryResponseDto save(JournalEntryRequestDto journalEntryRequestDto) {
        var journalEntry = JournalEntry.builder()
                .referencedNumber(journalEntryRequestDto.referencedNumber())
                .journalItemList(journalEntryRequestDto.journalItemList())
                .entryStatus(journalEntryRequestDto.entryStatus())
                .description(journalEntryRequestDto.description())
                .createdAt(LocalDateTime.now())
                .build();

        var savedJournalEntry = journalEntryRepository.save(journalEntry);

        return new JournalEntryResponseDto(savedJournalEntry.getId(),
                savedJournalEntry.getReferencedNumber(),
                savedJournalEntry.getDescription(),
                savedJournalEntry.getEntryStatus());
    }

    @Override
    public JournalEntryResponseDto findById(Long id) {
        var journalEntry = journalEntryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("journal entry",id)));

        return new JournalEntryResponseDto(journalEntry.getId(),
                journalEntry.getReferencedNumber(),
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
                                journalEntry.getReferencedNumber(),
                                journalEntry.getDescription(),
                                journalEntry.getEntryStatus()))
                        .toList();

        return new PageImpl<>(JournalEntries, pageable, JournalEntries.size());
    }
}
