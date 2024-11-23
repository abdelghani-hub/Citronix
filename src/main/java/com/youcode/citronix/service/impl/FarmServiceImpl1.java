package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.domain.Field;
import com.youcode.citronix.exception.AlreadyExistException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.service.FarmService;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.specification.FarmSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        if(farm.getArea() <= farm.getTotalFieldsArea()){
            throw new RuntimeException("Farm area must be greater than the sum of fields area");
        }
        // Validate fields
        List<Field> fields = farm.getFields();
        farm.setFields(new ArrayList<>());
        fields.forEach((Field field) -> {
                fieldService.validate(farm, field);
                farm.addField(field);
        });

        Farm savedFarm = farmRepository.save(farm);
        farm.getFields().forEach(field -> {
            savedFarm.setFields(new ArrayList<>());
            field.setFarm(savedFarm);
            fieldService.save(field);
        });
        return savedFarm;
    }

    @Override
    public Farm update(Farm farm, UUID id) {
        Farm farmToUpdate = farmRepository.findById(id).orElseThrow(() -> new RuntimeException("Farm not found"));
        farmToUpdate.setName(farm.getName());
        farmToUpdate.setLocation(farm.getLocation());
        farmToUpdate.setArea(farm.getArea());

        if (this.farmRepository.existsByNameAndIdNot(farm.getName(), farm.getId())) {
            throw new AlreadyExistException("Name", farm.getName());
        }

        if (farmToUpdate.getTotalFieldsArea() >= farm.getArea()) {
            throw new RuntimeException("Invalid Area : Total fields area must be less than the farm's total area");
        }

        return farmRepository.save(farmToUpdate);
    }

    @Override
    public Farm findById(UUID id) {
        return farmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farm"));
    }

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID farmId) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new ResourceNotFoundException("Farm"));
        farm.getFields().forEach(field -> fieldService.delete(field.getId()));
        farmRepository.delete(farm);
    }



    @Override
    public Page<Farm> filter(String name, String location, Double area, Pageable pageable) {
        Specification<Farm> specification = FarmSpecification.filterFarm(name, location, area);
        Page<Farm> farms = farmRepository.findAll(specification, pageable);
        return farms;
    }
}
