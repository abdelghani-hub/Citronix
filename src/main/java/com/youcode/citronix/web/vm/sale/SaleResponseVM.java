package com.youcode.citronix.web.vm.sale;

import java.time.LocalDate;

public class SaleResponseVM {

    private String id;
    private String unitPrice;
    private String clientName;
    private double quantity;
    private LocalDate date;
    private Double revenue;
    private String location;

    public SaleResponseVM() {
    }

    public SaleResponseVM(String unitPrice, String clientName, double quantity, LocalDate date, String location) {
        this.unitPrice = unitPrice;
        this.clientName = clientName;
        this.quantity = quantity;
        this.date = date;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
