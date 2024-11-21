package com.youcode.citronix.web.vm.tree;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TreeVM {

    @NotBlank(message = "Field is required.")
    private String fieldId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate plantingDate;

    public TreeVM() {
    }

    public TreeVM(String fieldId, LocalDate plantingDate) {
        this.fieldId = fieldId;
        this.plantingDate = plantingDate;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }
}
