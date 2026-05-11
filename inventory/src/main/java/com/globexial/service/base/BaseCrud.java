package com.globexial.service.base;

import org.springframework.data.domain.Page;

public interface  BaseCrud <T,E,ID>{
    T save(E e);
    T update(ID id,E e);
    T findById(ID id);
    Page<T> findAll(String pageNumber, String pageSize);
    void delete(ID id);
}
