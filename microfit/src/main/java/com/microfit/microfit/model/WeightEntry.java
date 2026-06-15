package com.microfit.microfit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "weight_entries")
public class WeightEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight;

    private String entryDate;

    public WeightEntry() {
    }

    public WeightEntry(Long id, Double weight, String entryDate) {
        this.id = id;
        this.weight = weight;
        this.entryDate = entryDate;
    }

    public Long getId() {
        return id;
    }

    public Double getWeight() {
        return weight;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}