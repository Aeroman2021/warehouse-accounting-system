package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.WareHouseRequestDto;
import com.globexial.model.dto.response.WareHouseResponseDto;
import com.globexial.model.entity.Warehouse;
import com.globexial.repository.WarehouseRepository;
import com.globexial.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public WareHouseResponseDto save(WareHouseRequestDto wareHouseRequestDto) {
        Warehouse warehouse = new Warehouse(null, wareHouseRequestDto.name());
        var savedWarehouse = warehouseRepository.save(warehouse);
        return new WareHouseResponseDto(savedWarehouse.getId(), savedWarehouse.getName());
    }

    @Override
    public WareHouseResponseDto update(Long id, WareHouseRequestDto wareHouseRequestDto) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("%s with id %d not found".formatted("warehouse", id)));

        warehouse.setName(wareHouseRequestDto.name());
        var update = warehouseRepository.save(warehouse);
        return new WareHouseResponseDto(update.getId(), update.getName());
    }

    @Override
    public WareHouseResponseDto findById(Long id) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("%s with id %d not found".formatted("warehouse", id)));
        return new WareHouseResponseDto(warehouse.getId(), warehouse.getName());
    }

    @Override
    public Page<WareHouseResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var warehouses = warehouseRepository.findAll(pageable)
                .stream()
                .map(e -> new WareHouseResponseDto(e.getId(), e.getName()))
                .toList();

        return new PageImpl<>(warehouses, pageable, warehouses.size());
    }

    @Override
    public void delete(Long id) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("%s with id %d not found".formatted("warehouse", id)));

        warehouseRepository.delete(warehouse);
    }
}
