package com.youcode.citronix.repository;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Page<Sale> findAllByHarvest(Harvest harvest, Pageable pageable);
    Page<Sale> findAll(Pageable pageable);
}
