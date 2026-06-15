package com.microfit.microfit.controller;

import com.microfit.microfit.model.WeightEntry;
import com.microfit.microfit.service.WeightEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weights")
@CrossOrigin("*")
public class WeightEntryController {

    private final WeightEntryService weightEntryService;

    public WeightEntryController(
            WeightEntryService weightEntryService) {
        this.weightEntryService = weightEntryService;
    }

    @PostMapping
    public WeightEntry addWeight(
            @RequestBody WeightEntry weightEntry) {

        return weightEntryService.addWeight(weightEntry);
    }

    @GetMapping
    public List<WeightEntry> getAllWeights() {

        return weightEntryService.getAllWeights();
    }
}