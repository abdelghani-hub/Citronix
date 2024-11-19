package com.youcode.citronix.web.api.v1.controller;


import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.service.FarmService;
import com.youcode.citronix.web.vm.farm.FarmResponseVM;
import com.youcode.citronix.web.vm.farm.FarmVM;
import com.youcode.citronix.web.vm.mapper.FarmResponseVmMapper;
import com.youcode.citronix.web.vm.mapper.FarmVmMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {


    private final FarmService farmService;
    private final FarmVmMapper farmVmMapper;
    private final FarmResponseVmMapper farmResponseVmMapper;

    public FarmController(@Qualifier("farmServiceImpl1") FarmService farmService, FarmVmMapper farmVmMapper, FarmResponseVmMapper farmResponseVmMapper) {
        this.farmService = farmService;
        this.farmVmMapper = farmVmMapper;
        this.farmResponseVmMapper = farmResponseVmMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM){
        Farm farm = farmVmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmResponseVmMapper.toVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.CREATED);
    }

    @PutMapping("/update/{farm_id}")
    public ResponseEntity<FarmVM> update(@PathVariable UUID farm_id, @RequestBody @Valid FarmVM farmVM){
        Farm farm = farmVmMapper.toEntity(farmVM);
        Farm updatedFarm = farmService.update(farm_id, farm);
        FarmVM farmResponseVM = farmVmMapper.toVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farm_id}")
    public ResponseEntity<String> delete(@PathVariable UUID farm_id){
        farmService.delete(farm_id);
        return new ResponseEntity<>("Farm delete successfully" , HttpStatus.OK);
    }

    @GetMapping("/find/{farm_id}")
    public ResponseEntity<FarmVM> findById(@PathVariable UUID farm_id){
        Farm farm = farmService.findById(farm_id);
        FarmVM farmResponseVM = farmVmMapper.toVM(farm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmVM>> findAll(Pageable pageable){
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmVM> farmResponseVM = farms.map(farmVmMapper::toVM);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }
}
