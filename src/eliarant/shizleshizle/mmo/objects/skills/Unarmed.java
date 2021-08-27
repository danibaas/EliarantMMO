package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import me.shizleshizle.core.objects.User;
import org.bukkit.inventory.ItemStack;

public class Unarmed extends CombatSkill {
    // disarming
    private final int Minimum_Disarm_Level = 30;
    private final double Disarm_Chance = 0.1;
    private final double Disarm_Chance_Increment = 1.09;
    private final int Max_Disarm_Chance = 33;
    private final int DisarmXP = 25;

    // iron grip (disarm prevention)
    private final int Minimum_Grip_Level = 60;
    private double Grip_Chance = 0.6;
    private final int GripXP = 20;

    public Unarmed(String owner) {
        super(owner, Skills.UNARMED);
        extraDamage = 1d / 3d;
    }

    public int getXPForDisarming() {
        return DisarmXP;
    }

    public int getXPForGrip() {
        return GripXP;
    }

    public double getDisarmChance() {
        if (getCurrentLevel() < Minimum_Disarm_Level) return 0;
        double lvlCalc = getCurrentLevel() - 30;
        if (lvlCalc < 5) return 0;
        lvlCalc = lvlCalc - (lvlCalc % 5);
        lvlCalc /= 5;
        double result = Disarm_Chance * Math.pow(Disarm_Chance_Increment, lvlCalc);
        if (result > Max_Disarm_Chance) result = Max_Disarm_Chance;
        return result;
    }

    public double getGrip_Chance() {
        if (getCurrentLevel() < Minimum_Grip_Level) return 0;
        Grip_Chance = (getCurrentLevel() / 100d);
        return Grip_Chance;
    }

    public boolean canBeDisarmed() {
        return (Grip_Chance != 1.0d);
    }

    public void disarm() {
        User p = User.getUser(getOwner());
        if (p != null) {
            ItemStack inHand = p.getItemInMainHand();
            p.setItemInHand(null);
            p.getWorld().dropItem(p.getLocation(), inHand);
        }
    }
}
