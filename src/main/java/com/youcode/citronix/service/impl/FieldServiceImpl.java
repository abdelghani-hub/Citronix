package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.repository.FieldRepository;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.service.TreeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private TreeService treeService;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    // Setter injection for TreeService
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }

    @Override
    public Field save(Field field) {
        this.validate(field.getFarm(), field);
        return fieldRepository.save(field);
    }

    @Override
    public Field update(Field field) {
        Field existingField = fieldRepository.findById(field.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Field"));

        Farm farm = existingField.getFarm();
        farm.removeField(existingField);
        validate(farm, field);
        farm.addField(field);

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(UUID fieldUuid) {
        return fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new ResourceNotFoundException("Field"));
    }

    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }

    @Override
    public Page<Field> findAllByFarm(UUID farmId, Pageable pageable) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() ->
                new ResourceNotFoundException("Farm"));
        return fieldRepository.findAllByFarm(farm, pageable);
    }

    @Override
    public void delete(UUID fieldId) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(() ->
                new ResourceNotFoundException("Field"));
        treeService.deleteAllByField(field);
        fieldRepository.delete(field);
    }

    @Override
    public void validate(Farm farm, Field newField) {
        double totalFieldsAreaExpected = farm.getTotalFieldsArea() + newField.getArea();

        if (newField.getArea() < 0.1) {
            throw new RuntimeException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

        if (newField.getArea() > (farm.getArea() * 0.5)) {
            throw new RuntimeException("Field area cannot exceed 50% of the farm's total area");
        }

        if (totalFieldsAreaExpected >= farm.getArea()) {
            throw new RuntimeException("Total field area must be less than the farm's total area");
        }

        if (farm.getFields().size() >= 10) {
            throw new RuntimeException("A farm cannot have more than 10 fields");
        }
    }
}