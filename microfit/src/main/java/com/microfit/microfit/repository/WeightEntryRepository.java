package com.microfit.microfit.repository;

import com.microfit.microfit.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightEntryRepository
        extends JpaRepository<WeightEntry, Long> {
}