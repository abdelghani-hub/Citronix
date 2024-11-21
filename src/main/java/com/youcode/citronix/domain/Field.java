package com.youcode.citronix.domain;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Field {

    public static final Double TreeSpacingForHectare = 0.01;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(mappedBy = "field",
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            fetch = FetchType.LAZY
    )
    private List<Tree> trees;

    public Field(){}

    public Field(UUID id, double area, Farm farm, List<Tree> trees) {
        this.id = id;
        this.area = area;
        this.farm = farm;
        this.trees = trees;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }

    public Field addTree(Tree tree) {
        this.trees.add(tree);
        return this;
    }

    public Field removeTree(Tree tree) {
        this.trees.remove(tree);
        return this;
    }

}
