package com.globexial.controller;

import com.globexial.model.dto.request.WareHouseRequestDto;
import com.globexial.model.dto.response.WareHouseResponseDto;
import com.globexial.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController( WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WareHouseResponseDto> save(@Valid @RequestBody WareHouseRequestDto requestDto) {
        var warehouse = warehouseService.save(requestDto);
        return new ResponseEntity<>(warehouse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WareHouseResponseDto> update(@Valid @RequestBody WareHouseRequestDto requestDto,
                                                       @PathVariable Long id) {
        var warehouse = warehouseService.update(id, requestDto);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WareHouseResponseDto> findById(@PathVariable Long id) {
        var warehouse = warehouseService.findById(id);
        return new ResponseEntity<>(warehouse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<WareHouseResponseDto>> findAll(@RequestParam String pageNumber,
                                                              @RequestParam String pageSize) {
        var warehouses = warehouseService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

}
