package com.microfit.microfit.controller;

import com.microfit.microfit.model.Workout;
import com.microfit.microfit.repository.WorkoutRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final WorkoutRepository workoutRepository;

    public DashboardController(
            WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboard() {

        List<Workout> workouts =
                workoutRepository.findAll();

        int totalWorkouts = workouts.size();

        int totalCaloriesBurned = workouts.stream()
                .mapToInt(Workout::getCaloriesBurned)
                .sum();

        double averageWorkoutDuration =
                workouts.stream()
                        .mapToInt(Workout::getDurationMinutes)
                        .average()
                        .orElse(0);

        Map<String, Object> response =
                new HashMap<>();

        response.put("totalWorkouts",
                totalWorkouts);

        response.put("totalCaloriesBurned",
                totalCaloriesBurned);

        response.put("averageWorkoutDuration",
                averageWorkoutDuration);

        return response;
    }
}