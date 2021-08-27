package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.objects.MMOLevelUpEvent;
import me.shizleshizle.core.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.YELLOW;

public class LvlUpEvent implements Listener {

    @EventHandler
    public void onLvlUp(MMOLevelUpEvent e) {
        User p = User.getUser(e.getPlayerName());
        if (p != null) {
            p.sendMessage(e.getSkill().getPrefix() + "You have leveled up to level " + GOLD + e.getCurrentLevel() + YELLOW + "!");
        }
    }
}
