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
@AllArgsConstructor
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String location;
    private double area;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields;
}
