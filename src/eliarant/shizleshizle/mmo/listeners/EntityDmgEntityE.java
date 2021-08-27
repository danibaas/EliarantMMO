package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.objects.KnockbackHelper;
import eliarant.shizleshizle.mmo.objects.skills.Axes;
import eliarant.shizleshizle.mmo.objects.skills.Swords;
import eliarant.shizleshizle.mmo.objects.skills.Unarmed;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDmgEntityE implements Listener {

    @EventHandler
    public void onEDmg(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            User damager = new User((Player) e.getDamager());
            if (isAxe(damager.getItemInMainHand())) {
                Axes axes = new Axes(damager.getName());
                final int axeLvl = axes.getCurrentLevel();
                final double extraDmg = axes.getDamage(axeLvl);
                double finalDmg = extraDmg + e.getDamage();
                final double knockbackChance = axes.getKnockbackChance(axeLvl);
                final int rando = Numbers.getRandom(0, axes.MaxPercentage);
                if (knockbackChance == axes.MaxPercentage || Numbers.approximatelyEqual(knockbackChance, rando, MMO.Standard_Deviation)) {
                    KnockbackHelper.knockback(e.getEntity(), damager.getUser(), axes.getKnockbackMultiplier());
                }
                final double critChance = axes.getCritChance();
                int critRandom = Numbers.getRandom(0, axes.MaxPercentage);
                if (critChance == axes.MaxPercentage || Numbers.approximatelyEqual(critChance, critRandom, MMO.Standard_Deviation)) {
                    finalDmg = axes.getCritValue(finalDmg);
                    damager.sendMessage(axes.getType().getPrefix() + ChatColor.RED + "***CRITICAL***");
                }
                e.setDamage(finalDmg);
                axes.addXP(axes.getXPFromEntityHit(e.getEntity()));
                axes.saveToFile();
            } else if (isSword(damager.getItemInMainHand())) {
                Swords swords = new Swords(damager.getName());
                final int swordLvl = swords.getCurrentLevel();
                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
                    swords.addXP(swords.getXpForSweeping());
                }
                final double dmg = swords.getDamage(swordLvl) + e.getDamage();
                final double knockbackChance = swords.getKnockbackChance(swordLvl);
                final int rando = Numbers.getRandom(0, swords.MaxPercentage);
                if (knockbackChance == swords.MaxPercentage || Numbers.approximatelyEqual(knockbackChance, rando, MMO.Standard_Deviation)) {
                    KnockbackHelper.knockback(e.getEntity(), damager.getUser(), swords.getKnockbackMultiplier());
                }
                final double bleedChance = swords.getBleedChance();
                int random = Numbers.getRandom(0, swords.MaxPercentage);
                if (bleedChance == swords.MaxPercentage || Numbers.approximatelyEqual(bleedChance, random, MMO.Standard_Deviation)) {
                    if (e.getEntity() instanceof LivingEntity) {
                        swords.bleed((LivingEntity) e.getEntity());
                        swords.addXP(swords.getXPForBleed());
                    }
                }
                e.setDamage(dmg);
                swords.addXP(swords.getXPFromEntityHit(e.getEntity()));
                swords.saveToFile();
            } else if (damager.getItemInMainHand().getType() == Material.AIR) {
                if (!(e.getEntity() instanceof Player)) {
                    Unarmed unarm = new Unarmed(damager.getName());
                    unarm.addXP(unarm.getXPFromEntityHit(e.getEntity()));
                    unarm.saveToFile();
                }
            }
            if (e.getEntity() instanceof Player) {
                User hit = new User((Player) e.getEntity());
                if (isSword(hit.getItemInMainHand())) {
                    Swords s = new Swords(hit.getName());
                    final double counterChance = s.getCounterChance();
                    final int random = Numbers.getRandom(0, s.MaxPercentage);
                    if (counterChance == s.MaxPercentage || Numbers.approximatelyEqual(counterChance, random, MMO.Standard_Deviation)) {
                        double dmg = e.getDamage();
                        dmg *= s.getCounterPercentage();
                        damager.getUser().damage(dmg);
                        hit.sendMessage(s.getType().getPrefix() + ChatColor.RED + "***COUNTERED***");
                        s.addXP(s.getXPForCounter());
                    }
                    s.saveToFile();
                } else if (damager.getItemInMainHand().getType() == Material.AIR) {
                    Unarmed unarm = new Unarmed(damager.getName());
                    Unarmed target = new Unarmed(hit.getName());
                    double disarm = unarm.getDisarmChance();
                    if (target.canBeDisarmed() && disarm > 0) {
                        int random = Numbers.getRandom(0, unarm.MaxPercentage);
                        double grip = target.getGrip_Chance();
                        if (grip > 0) {
                            int rando = Numbers.getRandom(0, unarm.MaxPercentage);
                            if (grip == unarm.MaxPercentage || Numbers.approximatelyEqual(grip, rando, MMO.Standard_Deviation)) {
                                target.addXP(target.getXPForGrip());
                            } else {
                                if (disarm == unarm.MaxPercentage || Numbers.approximatelyEqual(disarm, random, MMO.Standard_Deviation)) {
                                    target.disarm();
                                    unarm.addXP(unarm.getXPForDisarming());
                                }
                            }
                        } else {
                            if (disarm == unarm.MaxPercentage || Numbers.approximatelyEqual(disarm, random, MMO.Standard_Deviation)) {
                                target.disarm();
                                unarm.addXP(unarm.getXPForDisarming());
                            }
                        }
                        target.saveToFile();
                    }
                    unarm.addXP(unarm.getXPFromEntityHit(hit.getUser()));
                    unarm.saveToFile();
                }
            }
        }
    }

    boolean isAxe(ItemStack item) {
        return switch (item.getType()) {
            case WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE -> true;
            default -> false;
        };
    }

    boolean isSword(ItemStack item) {
        return switch (item.getType()) {
            case WOODEN_SWORD, STONE_SWORD, IRON_SWORD, GOLDEN_SWORD, DIAMOND_SWORD, NETHERITE_SWORD -> true;
            default -> false;
        };
    }
}
