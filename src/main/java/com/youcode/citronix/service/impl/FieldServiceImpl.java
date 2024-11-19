package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.repository.FieldRepository;
import com.youcode.citronix.service.FieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field save(Field field) {
        Farm farm = farmRepository.findById(field.getFarm().getId()).orElseThrow(() ->
                new RuntimeException("Farm not found"));

        return fieldRepository.save(field);
    }

    @Override
    public Field update(UUID fieldUuid, Field field) {
        Field existingField = fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new RuntimeException("Field not found"));

        Farm farm = existingField.getFarm();

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(UUID fieldUuid) {
        return fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new RuntimeException("Field not found"));
    }

    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID fieldUuid) {
        Field field = fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new RuntimeException("Field not found"));
        fieldRepository.delete(field);
    }
}
