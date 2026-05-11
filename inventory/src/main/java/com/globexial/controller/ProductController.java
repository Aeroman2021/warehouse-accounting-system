package com.globexial.controller;

import com.globexial.model.dto.request.ProductRequestDto;
import com.globexial.model.dto.response.ProductResponseDto;
import com.globexial.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@Valid @RequestBody ProductRequestDto requestDto) {
        var product = productService.save(requestDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@Valid @RequestBody ProductRequestDto requestDto, @PathVariable Long id) {
        var product = productService.update(id, requestDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
        var product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> findAll(@RequestParam String pageNumber,
                                                            @RequestParam String pageSize) {
        var products = productService.findAll(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
