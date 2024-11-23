package com.youcode.citronix.service;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FieldService {

    Field save(Field farm);
    Field update(Field farm, UUID id);
    Field findById(UUID id);
    Page<Field> findAll(Pageable pageable);
    void delete(UUID id);
    Page<Field> findAllByFarm(UUID farmId, Pageable pageable);

    void validate(Farm farm, Field field);
}
