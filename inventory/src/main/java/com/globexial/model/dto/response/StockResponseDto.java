package com.globexial.model.dto.response;

import com.globexial.model.entity.Product;
import com.globexial.model.entity.Warehouse;

import java.math.BigDecimal;


public record StockResponseDto(
        Long id,

        int quantity,

        BigDecimal avgCost,

        Product product,

        Warehouse wareHouse
) {


}
