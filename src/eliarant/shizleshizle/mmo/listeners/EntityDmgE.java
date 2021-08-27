package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.objects.skills.Acrobatics;
import eliarant.shizleshizle.mmo.utils.Skills;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.ChatColor.DARK_AQUA;

public class EntityDmgE implements Listener {

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        User p = new User((Player) e.getEntity());
        if (p.isGod()) return;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            double dmg = e.getDamage();
            Acrobatics acro = new Acrobatics(p.getName());
            double roll = acro.getRollChance(p.getInventory().getBoots());
            int rando = Numbers.getRandom(0, acro.MaxPercentage);
            if (roll == acro.MaxPercentage || Numbers.approximatelyEqual(roll, rando, MMO.Standard_Deviation)) {
                e.setDamage(0);
                e.setCancelled(true);
                p.sendMessage(Skills.ACROBATICS.getPrefix() + DARK_AQUA + "***ROLLED***");
                acro.addXP(acro.getRollXP());
            } else {
                double xp = acro.getXPPerDamage() * dmg;
                xp += acro.getXPAddition(p.getInventory().getBoots());
                if (p.isDead()) xp *= acro.getDeathMultiplier();
                acro.addXP((int) Math.round(xp));
            }
            acro.saveToFile();
        }
    }
}
