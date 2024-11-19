package com.youcode.citronix.web.vm.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youcode.citronix.domain.Field;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;


public class FarmVM {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Location is required.")
    private String location;

    @Positive(message = "Total Area must be positive.")
    private double area;

    private List<Field> fields;

    public FarmVM() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
