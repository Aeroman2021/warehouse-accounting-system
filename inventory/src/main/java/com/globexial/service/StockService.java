package com.globexial.service;

import com.globexial.model.dto.request.StockRequestDto;
import com.globexial.model.dto.response.StockResponseDto;
import com.globexial.service.base.BaseCrud;


public interface StockService extends BaseCrud<StockResponseDto, StockRequestDto,Long> {
}
