package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.HarvestDetail;
import com.youcode.citronix.repository.HarvestDetailRepository;
import com.youcode.citronix.service.HarvestDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HarvestDetailServiceImpl implements HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;

    public HarvestDetailServiceImpl(HarvestDetailRepository harvestDetailRepository) {
        this.harvestDetailRepository = harvestDetailRepository;
    }

    @Override
    public HarvestDetail save(HarvestDetail harvestDetail) {
        if(harvestDetail == null) {
            throw new RuntimeException("HarvestDetail cannot be null");
        }
        if (harvestDetail.getHarvest() == null) {
            throw new RuntimeException("HarvestDetail must have a Harvest");
        }
        if (harvestDetail.getTree() == null) {
            throw new RuntimeException("HarvestDetail must have a Tree");
        }
        if (harvestDetail.getQuantity() < 0) {
            throw new RuntimeException("HarvestDetail must have a Quantity and it must not be negative");
        }
        return harvestDetailRepository.save(harvestDetail);
    }

    @Override
    public List<HarvestDetail> saveAll(List<HarvestDetail> harvestDetails) {
        return harvestDetailRepository.saveAll(harvestDetails);
    }

    @Override
    public HarvestDetail update(HarvestDetail harvest, UUID harvestDetailId) {
        return null;
    }

    @Override
    public Page<HarvestDetail> findByHarvest(Harvest harvest, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteByHarvest(Harvest harvest) {

    }

    @Override
    public boolean existsByFieldAndSeasonAndYear(UUID fieldId, String harvestSeason, int harvestYear) {
        return harvestDetailRepository.existsByFieldIdAndHarvestSeasonAndHarvestYear(fieldId, harvestSeason, harvestYear);
    }
}
