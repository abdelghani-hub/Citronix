package com.youcode.citronix.web.vm.field;

import com.youcode.citronix.domain.Farm;

public class FieldResponseVM {

    private String id;
    private Farm farm;
    private double area;
    public FieldResponseVM() {
    }

    public FieldResponseVM(String id, Farm farm, double area) {
        this.id = id;
        this.farm = farm;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
