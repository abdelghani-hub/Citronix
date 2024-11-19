package com.youcode.citronix.service;

import com.youcode.citronix.domain.Farm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FarmService {

    Farm save(Farm farm);
    Farm update(UUID id, Farm farm);
    Farm findById(UUID id);
    Page<Farm> findAll(Pageable pageable);
    void delete(UUID id);
}
