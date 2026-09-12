package com.liferpg.life_rpg_backend.dto;

import com.liferpg.life_rpg_backend.entity.Task;
import java.util.List;

public class DashboardResponse {

    private String name;
    private int level;
    private int xp;
    private int gold;
    private int strength;
    private int intellect;
    private int wisdom;
    private int streak;
    private List<Task> tasks;

    public DashboardResponse() {
    }

    public DashboardResponse(
            String name,
            int level,
            int xp,
            int gold,
            int strength,
            int intellect,
            int wisdom,
            int streak,
            List<Task> tasks) {

        this.name = name;
        this.level = level;
        this.xp = xp;
        this.gold = gold;
        this.strength = strength;
        this.intellect = intellect;
        this.wisdom = wisdom;
        this.streak = streak;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getGold() {
        return gold;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntellect() {
        return intellect;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getStreak() {
        return streak;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}