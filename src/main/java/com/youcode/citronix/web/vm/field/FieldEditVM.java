package com.youcode.citronix.web.vm.field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class FieldEditVM {
    @NotBlank(message = "Field Id is required.")
    private String id;

    @Positive(message = "Area must be positive.")
    private double area;

    public FieldEditVM() {
    }

    public FieldEditVM(String id, double area) {
        this.id = id;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}