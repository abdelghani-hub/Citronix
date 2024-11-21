package com.youcode.citronix.web.vm.farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class FarmResponseVM {

    private String name;

    private String location;

    private double area;


    public FarmResponseVM() {
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
