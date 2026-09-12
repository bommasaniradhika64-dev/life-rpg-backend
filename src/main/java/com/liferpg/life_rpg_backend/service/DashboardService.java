package com.liferpg.life_rpg_backend.service;

import com.liferpg.life_rpg_backend.dto.DashboardResponse;
import com.liferpg.life_rpg_backend.entity.Character;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.CharacterRepository;
import com.liferpg.life_rpg_backend.repository.TaskRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;
    private final TaskRepository taskRepository;

    public DashboardService(
            UserRepository userRepository,
            CharacterRepository characterRepository,
            TaskRepository taskRepository) {

        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
        this.taskRepository = taskRepository;
    }

    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Character character = characterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        return new DashboardResponse(
                user.getName(),
                character.getLevel(),
                character.getXp(),
                character.getGold(),
                character.getStrength(),
                character.getIntellect(),
                character.getWisdom(),
                character.getStreak(),
                taskRepository.findByUser(user)
        );
    }
}