package com.liferpg.life_rpg_backend.controller;

import com.liferpg.life_rpg_backend.entity.Task;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.TaskRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;
import com.liferpg.life_rpg_backend.service.RPGService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rpg")
public class RPGController {

    private final RPGService rpgService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public RPGController(
            RPGService rpgService,
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.rpgService = rpgService;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // COMPLETE QUEST
    // =========================

    @PutMapping("/tasks/{taskId}/complete")
    public ResponseEntity<?> completeTask(
            @PathVariable Long taskId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                rpgService.completeTask(taskId, email)
        );
    }

    // =========================
    // QUEST HISTORY
    // =========================

    @GetMapping("/history")
    public ResponseEntity<List<Task>> getHistory(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Task> completedTasks = taskRepository
                .findByUser(user)
                .stream()
                .filter(Task::isCompleted)
                .sorted((a, b) -> {

                    if (a.getCompletedAt() == null &&
                            b.getCompletedAt() == null) {
                        return 0;
                    }

                    if (a.getCompletedAt() == null) {
                        return 1;
                    }

                    if (b.getCompletedAt() == null) {
                        return -1;
                    }

                    return b.getCompletedAt()
                            .compareTo(a.getCompletedAt());
                })
                .toList();

        return ResponseEntity.ok(completedTasks);
    }
}