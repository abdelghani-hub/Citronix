package com.youcode.citronix.domain;

import com.youcode.citronix.domain.enums.Season;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "total_quantity")
    private double totalQuantity;

    @Enumerated(EnumType.STRING)
    private Season season;

    private LocalDate date;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> sales;

    public Harvest() {
    }

    public Harvest(UUID id, double totalQuantity, Season season, LocalDate date, List<HarvestDetail> harvestDetails, List<Sale> sales) {
        this.id = id;
        this.totalQuantity = totalQuantity;
        this.season = season;
        this.date = date;
        this.harvestDetails = harvestDetails;
        this.sales = sales;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<HarvestDetail> getHarvestDetails() {
        return harvestDetails;
    }

    public void setHarvestDetails(List<HarvestDetail> harvestDetails) {
        this.harvestDetails = harvestDetails;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public double getQuantitySold() {
        return sales.stream().mapToDouble(Sale::getQuantity).sum();
    }

    public void addSale(Sale newSale) {
        sales.add(newSale);
    }

    public void removeSale(Sale existingSale) {
        sales.remove(existingSale);
    }


}
