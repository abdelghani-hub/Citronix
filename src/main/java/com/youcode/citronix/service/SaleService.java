package com.youcode.citronix.service;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SaleService {

    Sale save(Sale sale);
    Sale update(Sale sale, UUID saleId);
    Sale findById(UUID id);
    Page<Sale> findAll(Pageable pageable);
    void delete(UUID id);
    Page<Sale> findAllByHarvest(UUID harvestId, Pageable pageable);

    void validate(Sale sale);

    void deleteAllByHarvest(Harvest harvest);
}
