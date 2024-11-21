package com.youcode.citronix.web.vm.harvest;

import com.youcode.citronix.annotation.EnumValue;
import com.youcode.citronix.domain.enums.Season;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class HarvestVM {

    @NotBlank(message = "Field id is required.")
    private String fieldId;

    @EnumValue(enumClass = Season.class, message = "Invalid season, values (WINTER, SPRING, SUMMER, AUTUMN)")
    private String season;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public HarvestVM() {
    }

    public HarvestVM(String fieldId, String season, LocalDate date) {
        this.fieldId = fieldId;
        this.season = season;
        this.date = date;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
