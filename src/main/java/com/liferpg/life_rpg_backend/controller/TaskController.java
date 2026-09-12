package com.liferpg.life_rpg_backend.controller;

import com.liferpg.life_rpg_backend.dto.TaskRequest;
import com.liferpg.life_rpg_backend.entity.Task;
import com.liferpg.life_rpg_backend.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody TaskRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Task task = taskService.createTask(request, email);

        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getMyTasks(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                taskService.getMyTasks(email)
        );
    }
}