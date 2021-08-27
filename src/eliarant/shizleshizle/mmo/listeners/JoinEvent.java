package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.filehandling.FileHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!FileHandler.hasFile(e.getPlayer().getName())) {
            FileHandler.createFile(e.getPlayer().getName());
        }
    }
}
