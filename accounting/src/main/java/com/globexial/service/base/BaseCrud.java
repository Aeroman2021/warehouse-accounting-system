package com.globexial.service.base;

import org.springframework.data.domain.Page;

public interface  BaseCrud <T,E>{
    T save(E e);
    T findById(Long id);
    Page<T> findAll(String pageNumber, String pageSize);
}
