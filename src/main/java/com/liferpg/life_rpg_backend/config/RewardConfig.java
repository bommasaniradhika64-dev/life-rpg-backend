package com.liferpg.life_rpg_backend.config;

import com.liferpg.life_rpg_backend.entity.Reward;
import com.liferpg.life_rpg_backend.repository.RewardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RewardConfig {

    @Bean
    CommandLineRunner seedRewards(RewardRepository rewardRepository) {
        return args -> {

            if (rewardRepository.count() == 0) {

                rewardRepository.save(
                        new Reward(
                                "Warrior Badge",
                                "A badge for completing challenging quests.",
                                20,
                                "BADGE"
                        )
                );

                rewardRepository.save(
                        new Reward(
                                "Genius Badge",
                                "A badge for growing your Intellect.",
                                150,
                                "BADGE"
                        )
                );

                rewardRepository.save(
                        new Reward(
                                "Fire Theme",
                                "Unlock a fiery theme for your Life RPG.",
                                200,
                                "THEME"
                        )
                );

                rewardRepository.save(
                        new Reward(
                                "Crown Badge",
                                "A legendary badge for elite players.",
                                300,
                                "BADGE"
                        )
                );

                rewardRepository.save(
                        new Reward(
                                "XP Booster",
                                "A special collectible reward.",
                                250,
                                "BOOSTER"
                        )
                );

                System.out.println("🔥 Default rewards added successfully!");
            }
        };
    }
}