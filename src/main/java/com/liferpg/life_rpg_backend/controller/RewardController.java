package com.liferpg.life_rpg_backend.controller;

import com.liferpg.life_rpg_backend.entity.Inventory;
import com.liferpg.life_rpg_backend.entity.Reward;
import com.liferpg.life_rpg_backend.service.RewardService;
import com.liferpg.life_rpg_backend.repository.RewardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardRepository rewardRepository;
    private final RewardService rewardService;

    public RewardController(RewardRepository rewardRepository,
                            RewardService rewardService) {
        this.rewardRepository = rewardRepository;
        this.rewardService = rewardService;
    }

    @GetMapping
    public ResponseEntity<List<Reward>> getRewards() {
        return ResponseEntity.ok(rewardRepository.findAll());
    }

    @PostMapping("/{rewardId}/buy")
    public ResponseEntity<Inventory> buyReward(
            @PathVariable Long rewardId,
            Authentication authentication) {

        String email = authentication.getName();

        Inventory inventory =
                rewardService.buyReward(rewardId, email);

        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getInventory(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                rewardService.getMyInventory(email)
        );
    }
    @PutMapping("/inventory/{inventoryId}/equip")
    public ResponseEntity<Inventory> equipReward(
            @PathVariable Long inventoryId,
            Authentication authentication) {

        String email = authentication.getName();

        Inventory inventory =
                rewardService.equipReward(inventoryId, email);

        return ResponseEntity.ok(inventory);
    }
}