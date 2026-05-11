package com.globexial.service;

import com.globexial.model.dto.request.ProductRequestDto;
import com.globexial.model.dto.response.ProductResponseDto;
import com.globexial.service.base.BaseCrud;

public interface ProductService extends BaseCrud<ProductResponseDto,ProductRequestDto,Long> {

}
