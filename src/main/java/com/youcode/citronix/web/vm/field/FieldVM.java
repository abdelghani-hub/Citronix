package com.youcode.citronix.web.vm.field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class FieldVM {

    @NotBlank(message = "FarmId is required.")
    private String farmId;

    @Positive(message = "Area must be positive.")
    private double area;

    public FieldVM() {
    }

    public FieldVM(String farmId, double area) {
        this.farmId = farmId;
        this.area = area;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
