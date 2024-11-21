package com.youcode.citronix.web.vm.tree;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TreeEditVM {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate plantingDate;

    public TreeEditVM() {
    }

    public TreeEditVM(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }
}