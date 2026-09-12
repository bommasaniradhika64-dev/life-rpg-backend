package com.liferpg.life_rpg_backend.service;

import com.liferpg.life_rpg_backend.entity.Character;
import com.liferpg.life_rpg_backend.entity.Task;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.CharacterRepository;
import com.liferpg.life_rpg_backend.repository.TaskRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RPGService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    public RPGService(TaskRepository taskRepository,
                      UserRepository userRepository,
                      CharacterRepository characterRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
    }

    @Transactional
    public Character completeTask(Long taskId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.isCompleted()) {
            throw new RuntimeException("Task already completed");
        }

        Character character = characterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        // Mark task as completed
        task.setCompleted(true);
        task.setCompletedAt(java.time.LocalDateTime.now());

        // Add XP and Gold
        character.setXp(character.getXp() + task.getXpReward());
        character.setGold(character.getGold() + task.getGoldReward());

        // Increase attribute based on category
        if ("CODING".equalsIgnoreCase(task.getCategory())) {
            character.setIntellect(character.getIntellect() + 10);
        }
        else if ("GYM".equalsIgnoreCase(task.getCategory())) {
            character.setStrength(character.getStrength() + 10);
        }
        else if ("READING".equalsIgnoreCase(task.getCategory())
                || "STUDY".equalsIgnoreCase(task.getCategory())) {
            character.setWisdom(character.getWisdom() + 10);
        }

        // Update streak
        LocalDate today = LocalDate.now();
        LocalDate lastActivity = character.getLastActivityDate();

        if (lastActivity == null) {
            character.setStreak(1);
        }
        else if (lastActivity.equals(today)) {
            // Same day - don't increase streak
        }
        else if (lastActivity.equals(today.minusDays(1))) {
            character.setStreak(character.getStreak() + 1);
        }
        else {
            character.setStreak(1);
        }

        character.setLastActivityDate(today);

        // Level up
        while (character.getXp() >= 100 * character.getLevel() * character.getLevel()) {
            character.setLevel(character.getLevel() + 1);
        }

        taskRepository.save(task);
        return characterRepository.save(character);
    }
}