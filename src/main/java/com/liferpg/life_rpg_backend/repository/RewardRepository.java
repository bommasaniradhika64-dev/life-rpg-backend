package com.liferpg.life_rpg_backend.repository;

import com.liferpg.life_rpg_backend.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}