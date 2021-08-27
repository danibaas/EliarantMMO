package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CombatSkill extends Skill {
    protected double knockbackChance = 0.0;
    protected double extraDamage = 0.0;

    public CombatSkill(@NotNull String owner, @NotNull Skills type) {
        super(owner, type);
    }

    public double getDamage(double level) {
        return extraDamage * level;
    }

    public double getKnockbackChance(double level) {
        double result = level * knockbackChance * 100;
        if (result > 100) result = 100;
        return result;
    }

    public int getXPFromEntityHit(Entity t) {
        EntityType type = t.getType();
        if (t instanceof Animals) {
            return switch (type) {
                default -> 50;
                case PIG, SHEEP, COW, HORSE, DONKEY -> 55;
                case CHICKEN, OCELOT, WOLF -> 60;
            };
        } else if (t instanceof Mob) {
            if (t instanceof Boss) {
                return 150;
            } else {
                return 70;
            }
        } else if (t instanceof Player) {
            int toAdd = 80;
            Player p = (Player) t;
            for (ItemStack i : p.getInventory().getArmorContents()) {
                switch (i.getType()) {
                    case NETHERITE_BOOTS, NETHERITE_LEGGINGS, NETHERITE_CHESTPLATE, NETHERITE_HELMET -> toAdd += 60;
                    case DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_CHESTPLATE, DIAMOND_HELMET -> toAdd += 50;
                    case IRON_BOOTS, IRON_LEGGINGS, IRON_CHESTPLATE, IRON_HELMET -> toAdd += 40;
                    case CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET -> toAdd += 30;
                    case GOLDEN_BOOTS, GOLDEN_LEGGINGS, GOLDEN_CHESTPLATE, GOLDEN_HELMET -> toAdd += 20;
                    case LEATHER_BOOTS, LEATHER_LEGGINGS, LEATHER_CHESTPLATE, LEATHER_HELMET -> toAdd += 10;
                    default -> {
                    }
                }
            }
            return toAdd;
        } else {
            return 50;
        }
    }
}
