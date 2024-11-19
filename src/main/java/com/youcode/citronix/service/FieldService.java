package com.youcode.citronix.service;

import com.youcode.citronix.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FieldService {

    Field save(Field farm);
    Field update(UUID id, Field farm);
    Field findById(UUID id);
    Page<Field> findAll(Pageable pageable);
    void delete(UUID id);
}
