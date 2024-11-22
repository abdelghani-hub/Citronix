package com.youcode.citronix.service.impl;

import com.youcode.citronix.domain.Harvest;
import com.youcode.citronix.domain.Sale;
import com.youcode.citronix.exception.NotValidConstraintException;
import com.youcode.citronix.exception.NullOrBlankArgException;
import com.youcode.citronix.exception.ResourceNotFoundException;
import com.youcode.citronix.repository.SaleRepository;
import com.youcode.citronix.service.HarvestService;
import com.youcode.citronix.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestService harvestService;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestService harvestService) {
        this.saleRepository = saleRepository;
        this.harvestService = harvestService;
    }

    @Override
    public Sale save(Sale sale) {
        this.validate(sale);
        return saleRepository.save(sale);
    }

    @Override
    public Sale update(Sale sale, UUID saleId) {
        Sale existingSale = saleRepository.findById(saleId).orElseThrow(()-> new ResourceNotFoundException("Sale"));
        sale.getHarvest().removeSale(existingSale);
        validate(sale);
        sale.setId(saleId);
        return saleRepository.save(sale);
    }

    @Override
    public Sale findById(UUID id) {
        return saleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sale"));
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public void delete(UUID id) {
        Sale sale = this.findById(id);
        saleRepository.delete(sale);
    }

    @Override
    public Page<Sale> findAllByHarvest(UUID harvestId, Pageable pageable) {
        Harvest harvest = harvestService.findById(harvestId);
        return saleRepository.findAllByHarvest(harvest, pageable);
    }

    @Override
    public void validate(Sale sale) {
        if (sale == null) {
            throw new NullOrBlankArgException("Sale");
        }
        if (sale.getHarvest() == null) {
            throw new NullOrBlankArgException("Harvest");
        }
        if (sale.getDate().isBefore(sale.getHarvest().getDate())) {
            throw new NotValidConstraintException("Sale date cannot be before harvest date");
        }
        // check if the quantity to sell is available
        double quantityNotSold = sale.getHarvest().getTotalQuantity() - sale.getHarvest().getQuantitySold();
        if (sale.getQuantity() > quantityNotSold) {
            throw new NotValidConstraintException("There is just " + quantityNotSold + " Kg left to sell");
        }
    }

    @Override
    public void deleteAllByHarvest(Harvest harvest) {
        List<Sale> sales = harvestService.findById(harvest.getId()).getSales();
        saleRepository.deleteAll(sales);
    }
}