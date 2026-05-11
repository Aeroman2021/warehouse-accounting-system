package com.globexial.service;

import com.globexial.model.dto.request.StockMovementRequestDto;
import com.globexial.model.dto.response.StockMovementResponseDto;
import com.globexial.service.base.StockMovementCrud;

public interface StockMovementService extends StockMovementCrud<StockMovementResponseDto, StockMovementRequestDto> {
}
