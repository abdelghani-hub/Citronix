package com.youcode.citronix.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double area;


    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Harvest> harvests;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees;
}
