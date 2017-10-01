package com.anmed.storage.service;

import java.util.List;

public interface GeneralCRUDservice<T> {
    List<T> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}
