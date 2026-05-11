package com.globexial.service.base;

public interface PartyCrud<T,E,ID> {
    T save(E r);
    T findById(ID id);
}
