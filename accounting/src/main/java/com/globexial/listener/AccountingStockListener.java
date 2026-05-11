package com.globexial.listener;

import com.globexial.model.event.StockMovementCreatedEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountingStockListener {

    @EventListener
    public void handleStockMovementCreated(StockMovementCreatedEvent event){
        System.out.println("triggered");

        log.info(
                "Received stock movement event: {}",
                event.stockMovementId()
        );
    }
}
