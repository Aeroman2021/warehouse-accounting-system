package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.ProductRequestDto;
import com.globexial.model.dto.response.ProductResponseDto;
import com.globexial.model.entity.Product;
import com.globexial.repository.ProductRepository;
import com.globexial.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        var product = new Product(null, productRequestDto.name(), productRequestDto.sku());
        var savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getSku());
    }

    @Override
    public ProductResponseDto update(Long id, ProductRequestDto productRequestDto) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", id)));
        product.setName(productRequestDto.name());
        product.setSku(productRequestDto.sku());
        var savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getSku());
    }

    @Override
    public ProductResponseDto findById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", id)));
        return new ProductResponseDto(product.getId(), product.getName(), product.getSku());
    }

    @Override
    public Page<ProductResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var products =
                productRepository.findAll(pageable)
                        .stream()
                        .map(e -> new ProductResponseDto(e.getId(), e.getName(), e.getSku()))
                        .toList();

        return new PageImpl<>(products, pageable, products.size());
    }

    @Override
    public void delete(Long id) {
        var product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", id)));
        productRepository.delete(product);
    }


}
