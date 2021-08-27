package eliarant.shizleshizle.mmo.commands;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.utils.MMOSkill;
import eliarant.shizleshizle.mmo.utils.Skills;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.StringHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.YELLOW;

public class SkillCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Skills.isSkill(cmd.getName())) {
            if (sender instanceof Player) {
                Skills type = Skills.get(cmd.getName());
                if (type == null) return false;
                User p = new User((Player) sender);
                MMOSkill mmo = MMOSkill.getSkillFromFile(p.getName(), type);
                if (mmo == null) {
                    mmo = new MMOSkill(p.getName(), type, 0, 0);
                }
                String header = GOLD + "-={ " + YELLOW + StringHelper.normalCase(type.name()) + GOLD + " }=-";
                p.sendMessage(header);
                p.sendMessage(YELLOW + "Level: " + GOLD + mmo.getCurrentLevel());
                p.sendMessage(YELLOW + "Experience: " + GOLD + mmo.getCurrentXP() + YELLOW + "/" + GOLD + mmo.getTotalXPForCurrentLevel());
                p.sendMessage(YELLOW + "Description: " + GOLD + type.getDescription());
                // TODO: Addition skill info?
            } else {
                sender.sendMessage(MMO.PREFIX + "You must be a player to perform this command!");
            }
        }
        return false;
    }
}
