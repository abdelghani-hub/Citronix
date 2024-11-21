package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.exception.AlreadyExistException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.FarmRepository;
import com.youcode.citronix.service.FarmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImpl2 implements FarmService {

    private final FarmRepository farmRepository;

    public FarmServiceImpl2(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new AlreadyExistException("Name", farm.getName());
        }
        if(farm.getFields() != null || !farm.getFields().isEmpty()){
            throw new RuntimeException("Farm must not have any field");
        }

        return farmRepository.save(farm);
    }

    @Override
    public Farm update(Farm farm) {
        Farm farm1 = farmRepository.findById(farm.getId()).orElseThrow(() -> new ResourceNotFoundException("Farm"));
        farm1.setName(farm.getName());
        farm1.setLocation(farm.getLocation());
        farm1.setCreatedAt(farm.getCreatedAt());
        farm1.setArea(farm.getArea());
        return farmRepository.save(farm1);
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
        farmRepository.delete(farm);
    }
}
