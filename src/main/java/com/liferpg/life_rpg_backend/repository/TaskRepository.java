package com.liferpg.life_rpg_backend.repository;

import com.liferpg.life_rpg_backend.entity.Task;
import com.liferpg.life_rpg_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    Optional<Task> findByIdAndUser(Long id, User user);
}