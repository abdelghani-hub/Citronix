package com.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


    public int getAge() {
        return plantingDate != null ? LocalDate.now().getYear() - plantingDate.getYear() : 0;
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
