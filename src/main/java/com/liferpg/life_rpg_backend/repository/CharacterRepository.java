package com.liferpg.life_rpg_backend.repository;

import com.liferpg.life_rpg_backend.entity.Character;
import com.liferpg.life_rpg_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findByUser(User user);
}