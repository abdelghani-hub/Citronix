package com.youcode.citronix.web.vm.harvest;

import com.youcode.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.UUID;

public class HarvestResponseVM {

    private UUID id;
    private LocalDate date;
    private Season season;
    private String totalQuantity;

    public HarvestResponseVM() {
    }

    public HarvestResponseVM(UUID id, LocalDate date, Season season, String totalQuantity) {
        this.id = id;
        this.date = date;
        this.season = season;
        this.totalQuantity = totalQuantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
