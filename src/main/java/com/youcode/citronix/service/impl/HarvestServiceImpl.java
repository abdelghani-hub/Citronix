package com.youcode.citronix.service.impl;


import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.HarvestDetail;
import com.youcode.citronix.domain.Tree;
import com.youcode.citronix.exception.NotValidConstraintException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.HarvestRepository;
import com.youcode.citronix.service.HarvestDetailService;
import com.youcode.citronix.service.HarvestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailService harvestDetailService;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailService harvestDetailService) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailService = harvestDetailService;
    }

    @Override
    public Harvest save(Harvest harvest, Field field) {
        validate(harvest, field);
        List<HarvestDetail> harvestDetails = new ArrayList<>();
        List<Tree> trees = field.getTrees();
        trees.forEach(tree -> {
            // Create HarvestDetail for each tree
            if (!tree.getPlantingDate().isAfter(harvest.getDate())) {
                HarvestDetail harvestDetail = new HarvestDetail();
                harvestDetail.setTree(tree);
                harvestDetail.setHarvest(harvest);
                Double treeQte = tree.getAnnualProductivityForSeason();
                harvestDetail.setQuantity(treeQte);
                harvestDetails.add(harvestDetail);
                harvest.setTotalQuantity(harvest.getTotalQuantity() + treeQte);
            }
        });
        Harvest savedHarvest = harvestRepository.save(harvest);
        harvestDetails.forEach(
                harvestDetail -> {
                    harvestDetail.setHarvest(savedHarvest);
                    harvestDetailService.save(harvestDetail);
                }
        );
        return savedHarvest;
    }

    @Override
    public Harvest update(Harvest harvest, UUID harvestId) {
        return null; // todo : implement update
    }

    @Override
    public Harvest findById(UUID id) {
        return harvestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Harvest"));
    }

    @Override
    public Page<Harvest> findAll(Pageable pageable) {
        return harvestRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        Harvest harvest = findById(id);
        harvestDetailService.deleteByHarvest(harvest);
        harvestRepository.delete(harvest);
    }

    @Override
    public void validate(Harvest harvest, Field field) {
        // Empty Field from trees
        if (field.getTrees().size() == 0)
            throw new NotValidConstraintException("This Field has no trees to harvest!");

        // Harvest already exists
        if (
                harvestDetailService.existsByFieldAndSeasonAndYear(
                        field.getId(),
                        harvest.getSeason().toString(),
                        harvest.getDate().getYear()
                )
        )
            throw new NotValidConstraintException("There are already harvest details for this field in the same season and year");
    }
}
