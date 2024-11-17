package com.youcode.citronix.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "client_name")
    private String clientName;

    private double quantity;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "harvest_id")
    private Harvest harvest;


    public Double getRevenue() {
        return this.unitPrice * this.quantity;
    }
}
