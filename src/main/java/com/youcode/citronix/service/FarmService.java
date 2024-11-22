package com.youcode.citronix.service;

import com.youcode.citronix.domain.Farm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FarmService {

    Farm save(Farm farm);
    Farm update(Farm farm);
    Farm findById(UUID id);
    Page<Farm> findAll(Pageable pageable);
    void delete(UUID id);
    Page<Farm> filter(String name, String location, Double area, Pageable pageable);
}
