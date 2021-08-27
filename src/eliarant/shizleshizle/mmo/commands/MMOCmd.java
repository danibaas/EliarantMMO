package eliarant.shizleshizle.mmo.commands;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.utils.MMOSkill;
import eliarant.shizleshizle.mmo.utils.Skills;
import me.shizleshizle.core.objects.Messages;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.ErrorMessages;
import me.shizleshizle.core.utils.Numbers;
import me.shizleshizle.core.utils.StringHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.YELLOW;

public class MMOCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mmo")) {
            if (args.length == 0) {
                sender.sendMessage(MMO.PREFIX + "These are the current skills: ");
                for (Skills s : Skills.values()) {
                    sender.sendMessage(YELLOW + "- " + GOLD + StringHelper.normalCase(s.name()));
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("lookup")) {
                    String toLookup = args[1];
                    sender.sendMessage(MMO.PREFIX + "These are " + GOLD + toLookup + YELLOW + "'s levels!");
                    for (Skills s : Skills.values()) {
                        MMOSkill skill = MMOSkill.getSkillFromFile(toLookup, s);
                        if (skill != null) {
                            sender.sendMessage(GOLD + StringHelper.normalCase(s.name()) + ": " + YELLOW + skill.getCurrentLevel());
                        } else {
                            sender.sendMessage(GOLD + StringHelper.normalCase(s.name()) + ": " + YELLOW + "0");
                        }
                    }
                } else {
                    ErrorMessages.doErrorMessage(sender, Messages.INVALID_USAGE, "/mmo <addxp|removexp|setlevel|lookup> <player> [skill] [xp|level]");
                }
            } else if (args.length == 4) {
                User t = User.getUser(args[1]);
                if (t == null) {
                    ErrorMessages.doErrorMessage(sender, Messages.PLAYER_OFFLINE, args[1]);
                    return false;
                }
                int change;
                if (Numbers.isNumber(args[3])) {
                    change = Numbers.getInt(args[3]);
                } else {
                    sender.sendMessage(MMO.PREFIX + "You must enter a number!");
                    return false;
                }
                MMOSkill skill = MMOSkill.getSkillFromFile(t.getName(), Objects.requireNonNull(Skills.get(args[2])));
                if (skill == null) {
                    sender.sendMessage(MMO.PREFIX + "Something went wrong. Please contact the developers.");
                    return false;
                }
                final String pref = skill.getType().getPrefix();
                if (args[0].equalsIgnoreCase("addxp")) {
                    skill.addXP(change);
                    sender.sendMessage(pref + "You have added " + GOLD + change + YELLOW + " XP to " + GOLD + t.getName() + YELLOW + "!");
                } else if (args[0].equalsIgnoreCase("removexp")) {
                    skill.remove(change);
                    sender.sendMessage(pref + "You have removed " + GOLD + change + YELLOW + " XP to " + GOLD + t.getName() + YELLOW + "!");
                } else if (args[0].equalsIgnoreCase("setlevel")) {
                    skill.setLevel(change);
                    sender.sendMessage(pref + "You have set the level of " + GOLD + t.getName() + YELLOW + " to " + GOLD + change + YELLOW + "!");
                } else {
                    ErrorMessages.doErrorMessage(sender, Messages.INVALID_USAGE, "/mmo <addxp|removexp|setlevel> <player> <skill> <xp|level>");
                }
                skill.saveToFile();
            } else {
                ErrorMessages.doErrorMessage(sender, Messages.INVALID_USAGE, "/mmo <addxp|removexp|setlevel|lookup> <player> [skill] [xp|level]");
            }
        }
        return false;
    }
}
