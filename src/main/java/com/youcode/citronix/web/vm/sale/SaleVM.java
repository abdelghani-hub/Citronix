package com.youcode.citronix.web.vm.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SaleVM {

    @Positive(message = "Unit price must be positive.")
    private String unitPrice;

    @NotBlank(message = "Client name is required.")
    private String clientName;

    @Positive(message = "Quantity must be positive.")
    private double quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotBlank(message = "Harvest Id is required.")
    private String harvestId;

    public SaleVM() {
    }

    public SaleVM(String unitPrice, String clientName, double quantity, LocalDate date, String harvestId) {
        this.unitPrice = unitPrice;
        this.clientName = clientName;
        this.quantity = quantity;
        this.date = date;
        this.harvestId = harvestId;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(String harvestId) {
        this.harvestId = harvestId;
    }
}
