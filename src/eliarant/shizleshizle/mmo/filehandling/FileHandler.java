package eliarant.shizleshizle.mmo.filehandling;

import eliarant.shizleshizle.mmo.MMO;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileHandler {

    public static boolean hasFile(String name) {
        File f = new File(MMO.playerDox, name + ".yml");
        return f.exists();
    }

    public static boolean createFile(String name) {
        boolean createdFile = false;
        File f = new File(MMO.playerDox, name + ".yml");
        try {
            boolean created = f.createNewFile();
            createdFile = created;
            if (!created) {
                Bukkit.getLogger().info("MMO >> Files: Did not create file for player, does it already exist?");
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("MMO >> Files: " + e);
        }
        return createdFile;
    }

    // TODO: ? Make able to delete file?
    public static boolean deleteFile(String name) {
        File f = new File(MMO.playerDox, name + ".yml");
        boolean delete = f.delete();
        if (!delete) {
            Bukkit.getLogger().info("MMO >> Files: Could not delete player file!");
        }
        return delete;
    }

    public static void saveFile(String name) {
        File f = new File(MMO.playerDox, name + ".yml");
        FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
        try {
            conf.save(f);
        } catch (IOException e) {
            Bukkit.getLogger().info("MMO >> Files: Could not save player file!");
        }
    }

    @Nullable
    public static File getFile(String name) {
        if (hasFile(name)) {
            return new File(MMO.playerDox, name + ".yml");
        } else {
            boolean created = createFile(name);
            if (created) {
                return new File(MMO.playerDox, name + ".yml");
            } else {
                return null;
            }
        }
    }

    @Nullable
    public static FileConfiguration getConfig(String name) {
        if (hasFile(name)) {
            return YamlConfiguration.loadConfiguration(Objects.requireNonNull(getFile(name)));
        } else {
            return null;
        }
    }
}
