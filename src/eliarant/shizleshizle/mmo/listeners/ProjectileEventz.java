package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.objects.KnockbackHelper;
import eliarant.shizleshizle.mmo.objects.skills.Archery;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileEventz implements Listener {

    @EventHandler
    public void onLaunchProj(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            User p = new User((Player) e.getEntity());
            Archery arch = new Archery(p.getName());
            double infinityChance = arch.getInfinityChance();
            int random = Numbers.getRandom(0, arch.MaxPercentage);
            if (infinityChance == arch.MaxPercentage || Numbers.approximatelyEqual(infinityChance, random, MMO.Standard_Deviation)) {
                e.setConsumeItem(false);
                p.updateInventory();
            }
            arch.addXP(1);
            arch.saveToFile();
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arr = (Arrow) e.getEntity();
            if (arr.getShooter() instanceof Player) {
                User p = new User((Player) arr.getShooter());
                Entity hit = e.getHitEntity();
                if (hit != null) {
                    double dmg = arr.getDamage();
                    Archery arch = new Archery(p.getName());
                    final double distance = p.getLocation().distance(hit.getLocation());
                    final int xpForDistance = arch.getXpForDistance(distance);
                    final double extraDmg = arch.getDamage(arch.getCurrentLevel());
                    final double totalDmg = dmg + extraDmg;
                    arr.setDamage(totalDmg);
                    double knockbackChance = arch.getKnockbackChance(arch.getCurrentLevel());
                    final int rando = Numbers.getRandom(0, arch.MaxPercentage);
                    if (knockbackChance == arch.MaxPercentage || Numbers.approximatelyEqual(knockbackChance, rando, MMO.Standard_Deviation)) {
                        KnockbackHelper.knockback(hit, arr, arch.getKnockbackMultiplier());
                    }
                    final int xpToAdd = hit.isDead() ? 5 : 0;
                    arch.addXP(arch.getXPFromEntityHit(hit) + xpToAdd + xpForDistance);
                    arch.saveToFile();
                }
            }
        }
    }
}
