package com.liferpg.life_rpg_backend.service;

import com.liferpg.life_rpg_backend.dto.TaskRequest;
import com.liferpg.life_rpg_backend.entity.Task;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.TaskRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategory(request.getCategory());
        task.setDifficulty(request.getDifficulty());

        task.setXpReward(calculateXp(request.getDifficulty()));
        task.setGoldReward(calculateGold(request.getDifficulty()));

        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> getMyTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }

    private int calculateXp(String difficulty) {

        if (difficulty.equalsIgnoreCase("EASY")) {
            return 30;
        }

        if (difficulty.equalsIgnoreCase("MEDIUM")) {
            return 50;
        }

        if (difficulty.equalsIgnoreCase("HARD")) {
            return 80;
        }

        return 20;
    }

    private int calculateGold(String difficulty) {

        if (difficulty.equalsIgnoreCase("EASY")) {
            return 10;
        }

        if (difficulty.equalsIgnoreCase("MEDIUM")) {
            return 20;
        }

        if (difficulty.equalsIgnoreCase("HARD")) {
            return 40;
        }

        return 5;
    }
}