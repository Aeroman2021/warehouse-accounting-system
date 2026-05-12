package com.globexial.service.base;

import org.springframework.data.domain.Page;

public interface JournalEntryBaseCrud<T,E> {
    T findById(Long id);
    Page<T> findAll(String pageNumber, String pageSize);
    T process(E event);
}
