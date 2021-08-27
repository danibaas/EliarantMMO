package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;

public class Archery extends CombatSkill {
    // all in percentages from 0 to 1.
    private final double Infinity_Chance = 0.008;

    public Archery(String owner) {
        super(owner, Skills.ARCHERY);
        knockbackChance = 0.04;
        extraDamage = 4d / 15d;
    }

    public int getXpForDistance(double distance) {
        if (distance < 50) {
            return 15;
        } else if (distance < 100) {
            return 30;
        } else if (distance < 200) {
            return 50;
        } else {
            return 100;
        }
    }

    public double getInfinityChance() {
        double result = getCurrentLevel() * Infinity_Chance * 100;
        if (result > 100) result = 100;
        return result;
    }

    public int getKnockbackMultiplier() {
        int level = getCurrentLevel();
        if (level >= 20 && level < 40) {
            return 2;
        } else if (level >= 40 && level < 60) {
            return 3;
        } else if (level >= 60 && level < 80) {
            return 4;
        } else if (level >= 80) {
            return 5;
        } else {
            return 1;
        }
    }
}
