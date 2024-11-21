package com.youcode.citronix.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Entity
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "planting_date")
    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "tree")
    private List<HarvestDetail> harvestDetails;

    public Tree() {
    }

    public Tree(UUID id, LocalDate plantingDate, Field field, List<HarvestDetail> harvestDetails) {
        this.id = id;
        this.plantingDate = plantingDate;
        this.field = field;
        this.harvestDetails = harvestDetails;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public Tree setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
        return this;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<HarvestDetail> getHarvestDetails() {
        return harvestDetails;
    }

    public void setHarvestDetails(List<HarvestDetail> harvestDetails) {
        this.harvestDetails = harvestDetails;
    }

    public int getAge() {
        if(plantingDate == null )
            return 0;
        return (int) ChronoUnit.YEARS.between(plantingDate, LocalDate.now());
    }

    public double getAnnualProductivityForSeason() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12.0;
        } else if (age <= 20) {
            return 20.0;
        }
        // Constraint n°5
        return 0;
    }

    // Constraint n°6
    public boolean isPlantingSeason() {
        if (plantingDate == null) {
            return false;
        }
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}
