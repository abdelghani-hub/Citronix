package com.youcode.citronix.service;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Harvest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HarvestService {

    Harvest save(Harvest harvest, Field field);
    Harvest update(Harvest harvest, UUID harvestId);
    Harvest findById(UUID id);
    Page<Harvest> findAll(Pageable pageable);
    void delete(UUID id);

    void validate(Harvest harvest, Field field);
}
