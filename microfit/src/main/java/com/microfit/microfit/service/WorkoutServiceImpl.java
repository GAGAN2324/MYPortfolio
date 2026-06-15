package com.microfit.microfit.service;

import com.microfit.microfit.model.Workout;
import com.microfit.microfit.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout addWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Workout updateWorkout(Long id, Workout workout) {

        Workout existingWorkout =
                workoutRepository.findById(id).orElse(null);

        if (existingWorkout == null) {
            return null;
        }

        existingWorkout.setExerciseName(workout.getExerciseName());
        existingWorkout.setDurationMinutes(workout.getDurationMinutes());
        existingWorkout.setCaloriesBurned(workout.getCaloriesBurned());
        existingWorkout.setWorkoutDate(workout.getWorkoutDate());

        return workoutRepository.save(existingWorkout);
    }

    @Override
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getWorkoutsByUserId(Long userId) {
        return workoutRepository.findByUserId(userId);
    }
}