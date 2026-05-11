package com.globexial.controller;


import com.globexial.model.dto.request.StockRequestDto;
import com.globexial.model.dto.response.StockResponseDto;
import com.globexial.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockResponseDto> save(@Valid @RequestBody StockRequestDto requestDto) {
        var stock = stockService.save(requestDto);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDto> update(@Valid @RequestBody StockRequestDto requestDto, @PathVariable Long id) {
        var stock = stockService.update(id, requestDto);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> findById(@PathVariable Long id) {
        var stock = stockService.findById(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        stockService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<StockResponseDto>> findAll(@RequestParam String pageNumber,
                                                          @RequestParam String pageSize) {
        var products = stockService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
