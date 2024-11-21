package com.youcode.citronix.web.vm.tree;

import java.time.LocalDate;
import java.util.UUID;

public class TreeResponseVM {

    private UUID fieldId;
    private LocalDate plantingDate;
    private String farmName;

    public TreeResponseVM() {
    }

    public TreeResponseVM(UUID fieldId, LocalDate plantingDate) {
        this.fieldId = fieldId;
        this.plantingDate = plantingDate;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
}
