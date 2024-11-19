package com.youcode.citronix.web.vm.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class FarmEditVM {
    @NotBlank(message = "Farm Id is required.")
    private String id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Location is required.")
    private String location;

    @Positive(message = "Total Area must be positive.")
    private double area;

    public FarmEditVM() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}