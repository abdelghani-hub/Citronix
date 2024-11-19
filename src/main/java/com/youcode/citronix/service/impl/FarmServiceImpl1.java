package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.exception.AlreadyExistException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.service.FarmService;
import com.youcode.citronix.service.FieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class FarmServiceImpl1 implements FarmService {

    private final FarmRepository farmRepository;
    private final FieldService fieldService;

    public FarmServiceImpl1(FarmRepository farmRepository, FieldService fieldService) {
        this.farmRepository = farmRepository;
        this.fieldService = fieldService;
    }

    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new AlreadyExistException("Name", farm.getName());
        }

        if (farm.getFields().isEmpty()){
            throw new RuntimeException("Farm must have at least one field");
        }

        if(farm.getArea() <= farm.getFields().stream().mapToDouble(Field::getArea).sum()){
            throw new RuntimeException("Farm area must be greater than the sum of fields area");
        }

        farm.getFields().forEach(
                field -> validate(farm, field)
        );

        Farm savedFarm = farmRepository.save(farm);
        farm.getFields().forEach(field -> {
            field.setFarm(savedFarm);
            fieldService.save(field);
        });
        return savedFarm;
    }

    @Override
    public Farm update(UUID id, Farm farm) {
        Farm farm1 = farmRepository.findById(id).orElseThrow(() -> new RuntimeException("Farm not found"));
        farm1.setName(farm.getName());
        farm1.setLocation(farm.getLocation());
        farm1.setCreatedAt(farm.getCreatedAt());
        farm1.setArea(farm.getArea());
        return farmRepository.save(farm1);
    }

    @Override
    public Farm findById(UUID id) {
        return farmRepository.findById(id).orElseThrow(() -> new RuntimeException("Farm not found"));
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID farmId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new RuntimeException("Farm not found"));
        farmRepository.delete(farm);
    }

    private void validate(Farm farm, Field newField) {
        double totalFieldsArea = farm.getFields().stream().mapToDouble(Field::getArea).sum();

        if (newField.getArea() < 0.1) {
            throw new RuntimeException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

        if (newField.getArea() > (farm.getArea() * 0.5)) {
            throw new RuntimeException("Field area cannot exceed 50% of the farm's total area");
        }

        if (totalFieldsArea >= farm.getArea()) {
            throw new RuntimeException("Total field area must be less than the farm's total area");
        }

        if (farm.getFields().size() >= 10) {
            throw new RuntimeException("A farm cannot have more than 10 fields");
        }
    }
}
