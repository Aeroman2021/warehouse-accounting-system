package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.StockRequestDto;
import com.globexial.model.dto.response.StockResponseDto;
import com.globexial.model.entity.Stock;
import com.globexial.repository.ProductRepository;
import com.globexial.repository.StockRepository;
import com.globexial.repository.WarehouseRepository;
import com.globexial.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;


    @Override
    public StockResponseDto save(StockRequestDto stockRequestDto) {
        var productId = stockRequestDto.productId();
        var product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", productId)));

        var warehouseId = stockRequestDto.wareHouseId();
        var warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("warehouse", warehouseId)));

        Stock stock = new Stock(null, stockRequestDto.quantity(), stockRequestDto.avgCost(), product, warehouse);

        var savedStock = stockRepository.save(stock);
        return new StockResponseDto(savedStock.getId(),
                savedStock.getQuantity(),
                savedStock.getAverageCost(),
                savedStock.getProduct(),
                savedStock.getWareHouse());
    }

    @Override
    public StockResponseDto update(Long id, StockRequestDto stockRequestDto) {
        var stock = stockRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("stock", id)));

        var productId = stockRequestDto.productId();
        var product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", productId)));
        stock.setProduct(product);

        var warehouseId = stockRequestDto.wareHouseId();
        var warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("warehouse", warehouseId)));
        stock.setWareHouse(warehouse);

        stock.setQuantity(stockRequestDto.quantity());
        var savedStock = stockRepository.save(stock);

        return new StockResponseDto(savedStock.getId(),
                savedStock.getQuantity(),
                savedStock.getAverageCost(),
                savedStock.getProduct(),
                savedStock.getWareHouse());
    }

    @Override
    public StockResponseDto findById(Long id) {
        var stock = stockRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("stock", id)));

        return new StockResponseDto(stock.getId(),
                stock.getQuantity(),
                stock.getAverageCost(),
                stock.getProduct(),
                stock.getWareHouse());
    }

    @Override
    public Page<StockResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var stocks = stockRepository.findAll(pageable)
                .stream()
                .map(e -> new StockResponseDto(e.getId(), e.getQuantity(), e.getAverageCost(),
                        e.getProduct(), e.getWareHouse()))
                .toList();
        return new PageImpl<>(stocks, pageable, stocks.size());
    }

    @Override
    public void delete(Long id) {
        var stock = stockRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("stock", id)));

        stockRepository.delete(stock);
    }
}
