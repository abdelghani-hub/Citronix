package com.youcode.citronix.service;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.HarvestDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
public interface HarvestDetailService {
    HarvestDetail save(HarvestDetail harvest);
    HarvestDetail update(HarvestDetail harvest, UUID harvestDetailId);

    Page<HarvestDetail> findByHarvest(Harvest harvest, Pageable pageable);
    void deleteByHarvest(Harvest harvest);

    boolean existsByFieldAndSeasonAndYear(UUID fieldId, String harvestSeason, int harvestYear);

    List<HarvestDetail> saveAll(List<HarvestDetail> harvestDetails);
}
