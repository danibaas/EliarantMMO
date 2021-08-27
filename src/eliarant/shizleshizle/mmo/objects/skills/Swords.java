package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

public class Swords extends CombatSkill {
    // bleed vars
    private final double Bleed_Chance = 0.01;
    private final double Max_Bleed_Chance = 50;
    private final double Bleed_Length = 1;
    private final double Bleed_Damage = 1;
    private final double XP_For_Bleed = 30;

    // counter vars
    private final double CounterChance = 0.01;
    private final double Max_Counter_Chance = 50;
    private final double Percentage_To_Counter = 0.01;
    private final double Max_Percentage = 0.25;
    private final double XP_For_Counter = 30;

    private final int XP_For_Sweep = 5;


    public Swords(String owner) {
        super(owner, Skills.SWORDS);
        knockbackChance = 0.05;
        extraDamage = 1d / 3d;
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

    public int getXpForSweeping() {
        return XP_For_Sweep;
    }

    public int getXPForBleed() {
        return (int) Math.round(XP_For_Bleed * getBleedLength());
    }

    public double getBleedChance() {
        double result = Bleed_Chance * getCurrentLevel() * 100;
        if (result > Max_Bleed_Chance) result = Max_Bleed_Chance;
        return result;
    }

    public double getBleedLength() {
        if (getCurrentLevel() < 10) return 1;
        double toRound = getCurrentLevel() / 10d;
        double rounded = Math.round(toRound);
        if (rounded > 5) rounded = 5;
        return rounded * Bleed_Length;
    }

    public double getCounterChance() {
        double result = CounterChance * getCurrentLevel() * 100;
        if (result > Max_Counter_Chance) result = Max_Counter_Chance;
        return result;
    }

    public double getCounterPercentage() {
        int amountToMultiply = getCurrentLevel() - (getCurrentLevel() % 4);
        double counterPrcnt = amountToMultiply * Percentage_To_Counter;
        if (counterPrcnt > Max_Percentage) counterPrcnt = Max_Percentage;
        return counterPrcnt;
    }

    public int getXPForCounter() {
        double prcnt = getCounterPercentage() + 1;
        return (int) Math.round(prcnt * XP_For_Counter);
    }

    public void bleed(LivingEntity t) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MMO.p, () -> {
            t.damage(Bleed_Damage);
        }, 0, (long) (getBleedLength() * 20L));
    }
}
