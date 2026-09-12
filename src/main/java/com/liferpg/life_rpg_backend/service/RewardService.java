package com.liferpg.life_rpg_backend.service;

import com.liferpg.life_rpg_backend.entity.Character;
import com.liferpg.life_rpg_backend.entity.Inventory;
import com.liferpg.life_rpg_backend.entity.Reward;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.CharacterRepository;
import com.liferpg.life_rpg_backend.repository.InventoryRepository;
import com.liferpg.life_rpg_backend.repository.RewardRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardService {

    private final UserRepository userRepository;
    private final RewardRepository rewardRepository;
    private final CharacterRepository characterRepository;
    private final InventoryRepository inventoryRepository;

    public RewardService(UserRepository userRepository,
                         RewardRepository rewardRepository,
                         CharacterRepository characterRepository,
                         InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.rewardRepository = rewardRepository;
        this.characterRepository = characterRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getMyInventory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return inventoryRepository.findByUser(user);
    }

    public Inventory buyReward(Long rewardId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        Character character = characterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Character not found"));

        if (inventoryRepository.existsByUserAndReward(user, reward)) {
            throw new RuntimeException("You already own this reward");
        }

        if (character.getGold() < reward.getPrice()) {
            throw new RuntimeException("Not enough gold");
        }

        character.setGold(character.getGold() - reward.getPrice());
        characterRepository.save(character);

        Inventory inventory = new Inventory();
        inventory.setUser(user);
        inventory.setReward(reward);
        inventory.setPurchasedAt(LocalDateTime.now());
        inventory.setEquipped(false);

        return inventoryRepository.save(inventory);
    }
    public Inventory equipReward(Long inventoryId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inventory inventory = inventoryRepository.findByIdAndUser(inventoryId, user)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        List<Inventory> inventoryList = inventoryRepository.findByUser(user);

        for (Inventory item : inventoryList) {
            item.setEquipped(false);
        }

        inventoryRepository.saveAll(inventoryList);

        inventory.setEquipped(true);

        return inventoryRepository.save(inventory);
    }
}