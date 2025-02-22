package com.youcode.citronix.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;

    private String name;
    private String location;
    private double area;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "farm", fetch = FetchType.LAZY)
    private List<Field> fields;

    public Farm(){
        this.fields = new ArrayList<>();
        this.createdAt = LocalDate.now();
    }

    public Farm(UUID id, String name, String location, double area) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.area = area;
        this.createdAt = LocalDate.now();
        this.fields = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field){
        this.fields.add(field);
    }

    public void removeField(Field field){
        this.fields.remove(field);
    }

    public Double getTotalFieldsArea(){
        Double total = this.fields.stream().mapToDouble(Field::getArea).sum();
        return total;
    }

    public Double getRemainingArea(){
        return this.area - this.getTotalFieldsArea();
    }
}
