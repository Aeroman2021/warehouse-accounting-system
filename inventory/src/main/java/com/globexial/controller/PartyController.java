package com.globexial.controller;

import com.globexial.model.dto.request.PartyRequestDto;
import com.globexial.model.dto.response.PartyResponseDto;
import com.globexial.service.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyResponseDto> save(@Valid @RequestBody PartyRequestDto requestDto) {
        var savedParty = partyService.save(requestDto);
        return new ResponseEntity<>(savedParty, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyResponseDto> findById(@PathVariable Long id) {
        var party = partyService.findById(id);
        return new ResponseEntity<>(party, HttpStatus.OK);
    }

}
