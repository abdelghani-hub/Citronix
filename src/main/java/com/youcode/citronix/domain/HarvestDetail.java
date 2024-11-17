package com.youcode.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "harvest_uuid")
    private Harvest harvest;

    @ManyToOne
    @JoinColumn(name = "tree_uuid")
    private Tree tree;

}
