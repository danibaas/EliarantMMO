package eliarant.shizleshizle.mmo.utils;

import eliarant.shizleshizle.mmo.filehandling.FileHandler;
import eliarant.shizleshizle.mmo.objects.MMOLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

// formula for calculating xp:
// levelIncrementConstant^numberOfCurrentLevels * baseXP
public class MMOSkill {
    Skills type;
    int currentLevel;
    int xp;
    String owner;

    @Nullable
    public static MMOSkill getSkillFromFile(@NotNull String owner, @NotNull Skills type) {
        File f = FileHandler.getFile(owner);
        if (f == null) {
            Bukkit.getLogger().info("MMO > File does not exist!");
            return null;
        }
        FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
        String path = "skills." + type.name().toLowerCase();
        int currentLevel = conf.getInt(path + ".level", 0);
        int xp = conf.getInt(path + ".xp", 0);
        return new MMOSkill(owner, type, currentLevel, xp);
    }

    public MMOSkill(@NotNull String owner, @NotNull Skills type, int currentLevel, int xp) {
        this.type = type;
        this.currentLevel = currentLevel;
        this.xp = xp;
        this.owner = owner;
    }

    public MMOSkill(@NotNull String owner, @NotNull Skills type) {
        this.type = type;
        this.owner = owner;
        MMOSkill skill = getSkillFromFile(owner, type);
        if (skill != null) {
            currentLevel = skill.getCurrentLevel();
            xp = skill.getCurrentXP();
        } else {
            currentLevel = 0;
            xp = 0;
        }
    }

    public Skills getType() {
        return type;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentXP() {
        return xp;
    }

    public void addXP(int add) {
        int needed = getXPNeededForNextLevel() - xp;
        if (currentLevel >= type.getMaxLevel()) return;
        if (add >= needed) {
            currentLevel += 1;
            MMOLevelUpEvent lvlUp = new MMOLevelUpEvent(owner, type, currentLevel);
            Bukkit.getPluginManager().callEvent(lvlUp);
            xp = add - needed;
        } else {
            xp += add;
        }
    }

    public void remove(int rem) {
        rem = Math.abs(rem);
        if (rem >= xp) {
            rem -= xp;
            int prevLvlXP = getPreviousLevelXP();
            xp = prevLvlXP - rem;
        } else {
            xp -= rem;
        }

    }

    public int getXPNeededForNextLevel() {
        double multiplier = type.getXpLevelIncrement();
        double baseXP = type.getFirstLevelXP();
        if (currentLevel < type.getMaxLevel()) {
            double multiplied = Math.pow(multiplier, currentLevel);
            return (int) Math.ceil(multiplied * baseXP);
        } else {
            return 0;
        }
    }

    public int getTotalXPForCurrentLevel() {
        double powered = Math.pow(type.getXpLevelIncrement(), currentLevel);
        return (int) Math.ceil(powered * type.getFirstLevelXP());
    }

    public int getPreviousLevelXP() {
        if (currentLevel == 0) return 0;
        double multiplier = type.getXpLevelIncrement();
        double base = type.getFirstLevelXP();
        double multiplied = Math.pow(multiplier, currentLevel - 1);
        return (int) Math.ceil(multiplied * base);
    }

    public void setLevel(int level) {
        currentLevel = level;
    }

    public String getOwner() {
        return owner;
    }

    public void saveToFile() {
        File f = FileHandler.getFile(owner);
        if (f == null) {
            Bukkit.getLogger().info("MMO > File does not exist!");
            return;
        }
        FileConfiguration conf = FileHandler.getConfig(owner);
        if (conf == null) {
            Bukkit.getLogger().info("MMO > File configuration does not exist!");
            return;
        }
        String path = "skills." + type.name().toLowerCase();
        conf.set(path + ".level", currentLevel);
        conf.set(path + ".xp", xp);
        try {
            conf.save(f);
        } catch (IOException e) {
            Bukkit.getLogger().info("MMO > Error in saving skill file!");
        }
    }
}
