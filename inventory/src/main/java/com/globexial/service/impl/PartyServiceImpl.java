package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.PartyRequestDto;
import com.globexial.model.dto.response.PartyResponseDto;
import com.globexial.model.entity.Party;
import com.globexial.repository.PartyRepository;
import com.globexial.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @Override
    public PartyResponseDto save(PartyRequestDto requestDto) {
        var party = new Party();
        Party parent = null;
        if (requestDto.parentId() != null) {
            parent = partyRepository.findById(requestDto.parentId()).orElseThrow(() ->
                    new ResourceNotFoundException("%s with id %d not found".formatted("parent",
                            requestDto.parentId())));
            party.setParent(parent);
        }

        party.setPartyType(requestDto.partyType());
        party.setPartyRole(requestDto.partyRole());

        if (requestDto.nationalCode() != null)
            party.setNationalCode(requestDto.nationalCode());

        if (requestDto.economicCode() != null)
            party.setEconomicCode(requestDto.economicCode());

        var savedParty = partyRepository.save(party);
        return new PartyResponseDto(savedParty.getId(), savedParty.getNationalCode(), savedParty.getEconomicCode(),
                savedParty.getPartyType(), savedParty.getPartyRole(), party.getId());
    }

    @Override
    public PartyResponseDto findById(Long id) {
        var party = partyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("party", id)));

        return new PartyResponseDto(party.getId(), party.getNationalCode(), party.getEconomicCode(),
                party.getPartyType(), party.getPartyRole(), party.getId());
    }
}
