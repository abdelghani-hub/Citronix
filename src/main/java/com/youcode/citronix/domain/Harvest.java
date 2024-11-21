package com.youcode.citronix.domain;

import com.youcode.citronix.domain.enums.Season;
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
@AllArgsConstructor
@NoArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "total_quantity")
    private double totalQuantity;

    @Enumerated(EnumType.STRING)
    private Season season;

    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> sales;
}
