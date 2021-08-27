package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class Acrobatics extends Skill {

    public Acrobatics(String owner) {
        super(owner, Skills.ACROBATICS);
    }

    private final double Roll_Chance = 0.05;


    // Per 1 HP of damage you get the xp amount seen below.
    public int getXPPerDamage() {
        return 100;
    }

    public double getDeathMultiplier() {
        return 0.10;
    }

    public int getRollXP() {
        return 50;
    }

    public double getRollChance(@Nullable ItemStack boots) {
        int multiplier = (getCurrentLevel() - (getCurrentLevel() % 4)) / 4;
        double result = Roll_Chance * multiplier * 100;
        if (result > 75) result = 75;
        return result + getRollChanceAddition(boots);
    }

    private int getRollChanceAddition(@Nullable ItemStack boots) {
        if (boots == null) return 0;
        return switch (boots.getType()) {
            case LEATHER_BOOTS -> 3;
            case GOLDEN_BOOTS -> 6;
            case CHAINMAIL_BOOTS -> 9;
            case IRON_BOOTS -> 12;
            case DIAMOND_BOOTS -> 15;
            case NETHERITE_BOOTS -> 18;
            default -> 0;
        };
    }

    public int getXPAddition(@Nullable ItemStack boots) {
        if (boots == null) return 70;
        return switch (boots.getType()) {
            default -> 70;
            case LEATHER_BOOTS -> 60;
            case GOLDEN_BOOTS -> 50;
            case CHAINMAIL_BOOTS -> 40;
            case IRON_BOOTS -> 30;
            case DIAMOND_BOOTS -> 20;
            case NETHERITE_BOOTS -> 10;
        };
    }
}
