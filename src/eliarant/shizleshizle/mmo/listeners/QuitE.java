package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.utils.MMOSkill;
import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitE implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        for (Skills s : Skills.values()) {
            MMOSkill skillz = MMOSkill.getSkillFromFile(e.getPlayer().getName(), s);
            if (skillz != null) skillz.saveToFile();
        }
    }
}
