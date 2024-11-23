package com.youcode.citronix.web.api.v1.controller;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.Sale;
import com.youcode.citronix.service.HarvestService;
import com.youcode.citronix.service.SaleService;
import com.youcode.citronix.web.vm.sale.SaleResponseVM;
import com.youcode.citronix.web.vm.sale.SaleVM;
import com.youcode.citronix.web.vm.mapper.SaleVmMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleService saleService;
    private final HarvestService harvestService;
    private final SaleVmMapper saleVmMapper;

    public SaleController(SaleService saleService, HarvestService harvestService, SaleVmMapper saleVmMapper) {
        this.saleService = saleService;
        this.harvestService = harvestService;
        this.saleVmMapper = saleVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<SaleResponseVM> save(@RequestBody @Valid SaleVM saleVM) {
        Sale sale = saleVmMapper.toEntity(saleVM);
        Harvest saleHarvest = harvestService.findById(UUID.fromString(saleVM.getHarvestId()));
        sale.setHarvest(saleHarvest);
        Sale savedSale = saleService.save(sale);
        SaleResponseVM response = saleVmMapper.toSaleResponseVM(savedSale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{saleId}")
    public ResponseEntity<SaleResponseVM> update(@PathVariable UUID saleId, @RequestBody @Valid SaleVM saleVM) {
        Sale sale = saleVmMapper.toEntity(saleVM);
        Harvest saleHarvest = harvestService.findById(UUID.fromString(saleVM.getHarvestId()));
        sale.setHarvest(saleHarvest);
        Sale updatedSale = saleService.update(sale, saleId);
        SaleResponseVM response = saleVmMapper.toSaleResponseVM(updatedSale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponseVM> findById(@PathVariable UUID saleId) {
        Sale sale = saleService.findById(saleId);
        SaleResponseVM saleResponseVM = saleVmMapper.toSaleResponseVM(sale);
        return new ResponseEntity<>(saleResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SaleResponseVM>> findAllByFarm(Pageable pageable) {
        Page<Sale> sales = saleService.findAll(pageable);
        Page<SaleResponseVM> salesResponseVMs = sales.map(
                s -> saleVmMapper.toSaleResponseVM(s)
        );
        return new ResponseEntity<>(salesResponseVMs, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{saleId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID saleId) {
        saleService.delete(saleId);
        return new ResponseEntity<>(Map.of("message", "Sale deleted successfully"), HttpStatus.OK);
    }
}