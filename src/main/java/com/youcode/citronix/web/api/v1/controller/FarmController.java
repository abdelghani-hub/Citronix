package com.youcode.citronix.web.api.v1.controller;


import com.youcode.citronix.domain.Farm;
import com.youcode.citronix.service.FarmService;
import com.youcode.citronix.web.vm.farm.FarmEditVM;
import com.youcode.citronix.web.vm.farm.FarmResponseVM;
import com.youcode.citronix.web.vm.farm.FarmVM;
import com.youcode.citronix.web.vm.mapper.FarmEditVmMapper;
import com.youcode.citronix.web.vm.mapper.FarmResponseVmMapper;
import com.youcode.citronix.web.vm.mapper.FarmVmMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {


    private final FarmService farmService;
    private final FarmVmMapper farmVmMapper;
    private final FarmResponseVmMapper farmResponseVmMapper;
    private final FarmEditVmMapper farmEditVmMapper;

    public FarmController(@Qualifier("farmServiceImpl1") FarmService farmService, FarmVmMapper farmVmMapper, FarmResponseVmMapper farmResponseVmMapper, FarmEditVmMapper farmEditVmMapper) {
        this.farmService = farmService;
        this.farmVmMapper = farmVmMapper;
        this.farmResponseVmMapper = farmResponseVmMapper;
        this.farmEditVmMapper = farmEditVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM){
        Farm farm = farmVmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmResponseVmMapper.toVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<FarmResponseVM> update(@RequestBody @Valid FarmEditVM farmEditVM){
        Farm farm = farmEditVmMapper.toEntity(farmEditVM);
        Farm updatedFarm = farmService.update(farm);
        FarmResponseVM farmResponseVM = farmResponseVmMapper.toVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{farm_id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String farm_id){
        farmService.delete(UUID.fromString(farm_id));
        return new ResponseEntity<>(Map.of("message", "Farm deleted successfully") , HttpStatus.OK);
    }

    @GetMapping("/{farm_id}")
    public ResponseEntity<FarmResponseVM> findById(@PathVariable UUID farm_id){
        Farm farm = farmService.findById(farm_id);
        FarmResponseVM farmResponseVM = farmResponseVmMapper.toVM(farm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FarmResponseVM>> findAll(Pageable pageable){
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmResponseVM> farmResponseVM = farms.map(farmResponseVmMapper::toVM);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }
}
