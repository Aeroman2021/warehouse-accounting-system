package com.globexial.controller;

import com.globexial.excception.InsufficientStockException;
import com.globexial.model.dto.request.StockMovementRequestDto;
import com.globexial.model.dto.response.StockMovementResponseDto;
import com.globexial.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementRepository;

    @PostMapping
    public ResponseEntity<StockMovementResponseDto> saveStockMovementTransaction(@Valid @RequestBody StockMovementRequestDto requestDto)
            throws InsufficientStockException {
        var stockMovement = stockMovementRepository.saveStockMovementTransaction(requestDto);
        return new ResponseEntity<>(stockMovement, HttpStatus.CREATED);
    }

}
