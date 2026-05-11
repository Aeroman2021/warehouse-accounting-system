package com.globexial.service.impl;

import com.globexial.excception.InsufficientStockException;
import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.StockMovementRequestDto;
import com.globexial.model.dto.response.StockMovementResponseDto;
import com.globexial.model.entity.Stock;
import com.globexial.model.entity.StockMovement;
import com.globexial.model.enums.MovementType;
import com.globexial.model.event.StockMovementCreatedEvent;
import com.globexial.repository.PartyRepository;
import com.globexial.repository.StockMovementRepository;
import com.globexial.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockMovementServiceImpl implements com.globexial.service.StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final StockRepository stockRepository;
    private final PartyRepository partyRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    public StockMovementResponseDto saveStockMovementTransaction(StockMovementRequestDto stockMovementRequestDto) {

        var stock = stockRepository.findById(stockMovementRequestDto.stockId()).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("stock",
                        stockMovementRequestDto.stockId())));

        var party = partyRepository.findById(stockMovementRequestDto.partyId()).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("party",
                        stockMovementRequestDto.partyId())));

        int updatedQuantity = quantityUpdater(stockMovementRequestDto, stock);
        stock.setQuantity(updatedQuantity);
        stockRepository.save(stock);
        log.info("Stock with id {} updated from {} to {}" ,
                stock.getId(),
                stock.getQuantity(),
                updatedQuantity);

        StockMovement stockMovement = new StockMovement();
        stockMovement.setParty(party);
        stockMovement.setQuantity(stockMovementRequestDto.quantity());
        stockMovement.setMovementType(stockMovementRequestDto.movementType());
        stockMovement.setStock(stock);
        stockMovement.setUnitPrice(stockMovementRequestDto.unitPrice());
        var totalPrice = stockMovementRequestDto.unitPrice()
                .multiply(BigDecimal.valueOf(stockMovementRequestDto.quantity()))
                .setScale(2, RoundingMode.HALF_UP);
        stockMovement.setTotalPrice(totalPrice);
        stockMovement.setReferenceNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        stockMovement.setDescription(stockMovementRequestDto.description());
        stockMovement.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        var savedStockMovement = stockMovementRepository.save(stockMovement);
        log.info("StockMovement with id {} created" , savedStockMovement.getId());

        eventPublisher.publishEvent(new StockMovementCreatedEvent(
                savedStockMovement.getId(),
                stock.getId(),
                party.getId(),
                savedStockMovement.getMovementType(),
                savedStockMovement.getQuantity(),
                savedStockMovement.getTotalPrice(),
                stock.getAverageCost(),
                savedStockMovement.getCreatedAt()
        ));

        log.info("Publishing stock movement event...");

        return new StockMovementResponseDto(savedStockMovement.getId(),
                savedStockMovement.getStock(), savedStockMovement.getQuantity(), savedStockMovement.getTotalPrice(),
                savedStockMovement.getParty(), savedStockMovement.getMovementType());
    }

    private int quantityUpdater(StockMovementRequestDto stockMovementRequestDto, Stock stock) {
        var movementType = stockMovementRequestDto.movementType();
        var quantity = stock.getQuantity();
        if(quantityIsIncreased(movementType)){
            return quantity + stockMovementRequestDto.quantity();
        }else{
            if (!stockIsSufficient(stockMovementRequestDto, stock)){
                log.info("There is not sufficient stock exist");
                throw new InsufficientStockException();
            }
            return quantity - stockMovementRequestDto.quantity();
        }
    }


    private boolean quantityIsIncreased(MovementType movementType) {
        return switch (movementType) {
            case RETURN, PURCHASE -> true;
            default -> false;
        };
    }

    private boolean stockIsSufficient(StockMovementRequestDto requestDto, Stock stock) {
        return requestDto.quantity() < stock.getQuantity();
    }

}
