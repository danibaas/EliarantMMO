package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;

public class Axes extends CombatSkill {
    private final double Critical_Chance = 7d / 10d;
    private final int Max_Crit_Chance = 70;
    private final double Crit_Dmg_Multiplier = 1.5;

    public Axes(String owner) {
        super(owner, Skills.AXES);
        knockbackChance = 0.02;
        extraDamage = 1d / 6d;
    }

    public int getKnockbackMultiplier() {
        if (getCurrentLevel() < 10) return 1;
        double numberToFloor = getCurrentLevel() / 10.0;
        int flooredNmbr = (int) Math.floor(numberToFloor);
        return switch (flooredNmbr) {
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            case 5 -> 5;
            case 6 -> 6;
            case 7 -> 7;
            case 8 -> 8;
            case 9 -> 9;
            case 10 -> 10;
            default -> 1;
        };
    }

    public double getCritChance() {
        double crit = Critical_Chance * getCurrentLevel() * 100;
        if (crit > Max_Crit_Chance) crit = Max_Crit_Chance;
        return crit;
    }

    public double getCritValue(double dmg) {
        return dmg * Crit_Dmg_Multiplier;
    }
}
