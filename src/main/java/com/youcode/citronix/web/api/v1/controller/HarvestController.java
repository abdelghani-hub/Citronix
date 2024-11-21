package com.youcode.citronix.web.api.v1.controller;

import com.youcode.citronix.domain.Field;
import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.service.FieldService;
import com.youcode.citronix.service.HarvestService;
import com.youcode.citronix.web.vm.harvest.HarvestResponseVM;
import com.youcode.citronix.web.vm.harvest.HarvestVM;
import com.youcode.citronix.web.vm.mapper.HarvestVmMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
public class HarvestController {

    private final HarvestService harvestService;
    private final FieldService fieldService;
    private final HarvestVmMapper harvestVmMapper;

    public HarvestController(HarvestService harvestService, FieldService fieldService, HarvestVmMapper harvestVmMapper) {
        this.harvestService = harvestService;
        this.fieldService = fieldService;
        this.harvestVmMapper = harvestVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<HarvestResponseVM> save(@RequestBody @Valid HarvestVM harvestVM) {
        Harvest harvest = harvestVmMapper.toEntity(harvestVM);
        Field harvestField = fieldService.findById(UUID.fromString(harvestVM.getFieldId()));
        Harvest savedHarvest = harvestService.save(harvest, harvestField);
        HarvestResponseVM response = harvestVmMapper.toHarvestResponseVM(savedHarvest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{harvestId}")
    public ResponseEntity<HarvestResponseVM> findById(@PathVariable UUID harvestId) {
        Harvest harvest = harvestService.findById(harvestId);
        HarvestResponseVM harvestResponseVM = harvestVmMapper.toHarvestResponseVM(harvest);
        return new ResponseEntity<>(harvestResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<HarvestResponseVM>> findAllByFarm(Pageable pageable) {
        Page<Harvest> harvests = harvestService.findAll(pageable);
        Page<HarvestResponseVM> harvestsResponseVMs = harvests.map(
                h->{
                    HarvestResponseVM harvestResponseVM = harvestVmMapper.toHarvestResponseVM(h);
                    return harvestResponseVM;
                }
        );
        return new ResponseEntity<>(harvestsResponseVMs, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{harvestId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID harvestId) {
        harvestService.delete(harvestId);
        return new ResponseEntity<>(Map.of("message", "Harvest deleted successfully"), HttpStatus.OK);
    }
}