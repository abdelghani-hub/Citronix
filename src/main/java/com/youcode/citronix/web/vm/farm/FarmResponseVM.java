package com.youcode.citronix.web.vm.farm;

public class FarmResponseVM {

    private String id;
    private String name;
    private String location;
    private double area;


    public FarmResponseVM() {
    }

    public FarmResponseVM(String name, String location, double area) {
        this.name = name;
        this.location = location;
        this.area = area;
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
