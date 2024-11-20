package com.youcode.citronix.web.vm.field;

import com.youcode.citronix.domain.Farm;

public class FieldResponseVM {

    private Farm farm;

    private double area;

    public FieldResponseVM() {
    }

    public FieldResponseVM(Farm farm, double area) {
        this.farm = farm;
        this.area = area;
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
