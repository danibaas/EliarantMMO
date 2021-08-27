package eliarant.shizleshizle.mmo;

import eliarant.shizleshizle.mmo.commands.MMOCmd;
import eliarant.shizleshizle.mmo.commands.SkillCmd;
import eliarant.shizleshizle.mmo.listeners.*;
import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static org.bukkit.ChatColor.*;

public class MMO extends JavaPlugin {
    public static Plugin p;
    public static File playerDox;
    public static String PREFIX = YELLOW.toString() + BOLD + "MMO" + GOLD + " >> " + YELLOW;
    public static final int Standard_Deviation = 5;

    public void onEnable() {
        getLogger().info("MMO > Enabling...");
        long start = System.currentTimeMillis();
        p = this;
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        playerDox = new File(getDataFolder(), "playerskills");
        if (!playerDox.exists() || !playerDox.isDirectory()) {
            if (playerDox.mkdir()) getLogger().info("MMO > Created skills folder!");
        }
        registerCommands();
        registerEvents();
        long end = System.currentTimeMillis() - start;
        getLogger().info("MMO > Enabled! (" + end + " ms)");
    }

    public void onDisable() {
        getLogger().info("MMO > Disabling...");
        long start = System.currentTimeMillis();

        long end = System.currentTimeMillis() - start;
        getLogger().info("MMO > Disabled! (" + end + " ms)");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new LvlUpEvent(), this);
        pm.registerEvents(new BreakBlockE(), this);
        pm.registerEvents(new EntityDmgE(), this);
        pm.registerEvents(new QuitE(), this);
        pm.registerEvents(new ProjectileEventz(), this);
        pm.registerEvents(new EntityDmgEntityE(), this);
        pm.registerEvents(new FishE(), this);
    }

    private void registerCommands() {
        for (Skills cmd : Skills.values()) {
            Objects.requireNonNull(getCommand(cmd.name().toLowerCase())).setExecutor(new SkillCmd());
        }
        Objects.requireNonNull(getCommand("mmo")).setExecutor(new MMOCmd());
    }
}
