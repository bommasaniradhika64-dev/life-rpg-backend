package com.liferpg.life_rpg_backend.repository;

import com.liferpg.life_rpg_backend.entity.Inventory;
import com.liferpg.life_rpg_backend.entity.Reward;
import com.liferpg.life_rpg_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByUser(User user);

    boolean existsByUserAndReward(User user, Reward reward);
    Optional<Inventory> findByIdAndUser(Long id, User user);
}