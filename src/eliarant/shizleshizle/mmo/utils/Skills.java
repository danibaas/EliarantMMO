package eliarant.shizleshizle.mmo.utils;

import javax.annotation.Nullable;

import static org.bukkit.ChatColor.*;

public enum Skills {

    // COMBAT
    ACROBATICS(7800, 1.205, 100, YELLOW.toString() + BOLD + "Acrobatics" + GOLD + " >> " + YELLOW, "Acrobatics is a skill focused on moving, reducing fall damage and dodging attacks."),
    ARCHERY(7600, 1.205, 100, YELLOW.toString() + BOLD + "Archery" + GOLD + " >> " + YELLOW, "Archery is a skill focused on shooting and hitting entities with an arrow."),
    AXES(9300, 1.205, 100, YELLOW.toString() + BOLD + "Axes" + GOLD + " >> " + YELLOW, "Axes is a skill focused on combat with an axe."),
    SWORDS(12000, 1.205, 100, YELLOW.toString() + BOLD + "Swords" + GOLD + " >> " + YELLOW, "Swords is a skill focused on combat with a sword."),
    UNARMED(61350, 1.205, 100, YELLOW.toString() + BOLD + "Unarmed" + GOLD + " >> " + YELLOW, "Unarmed is a skill focused on combat with your bare hand."),

    // GATHERING
    EXCAVATION(8100, 1.205, 100, YELLOW.toString() + BOLD + "Excavation" + GOLD + " >> " + YELLOW, "Excavation is a skill focused on mining sand, gravel, dirt and the like with a shovel."),
    FISHING(8300, 1.205, 100, YELLOW.toString() + BOLD + "Fishing" + GOLD + " >> " + YELLOW, "Fishing is a skill focused on fishing with a fishing rod."),
    HERBALISM(7500, 1.205, 100, YELLOW.toString() + BOLD + "Herbalism" + GOLD + " >> " + YELLOW, "Herbalism is a skill focused on farming crops with hand and creating farmland using a hoe."),
    MINING(8100, 1.205, 100, YELLOW.toString() + BOLD + "Mining" + GOLD + " >> " + YELLOW, "Mining is a skill focused on mining with a pickaxe."),
    WOODCUTTING(6500, 1.205, 100, YELLOW.toString() + BOLD + "Woodcutting" + GOLD + " >> " + YELLOW, "Woodcutting is a skill focused on cutting (mushroom) trees with an axe."),

    // MISC
    REPAIR(8000, 1.205, 100, YELLOW.toString() + BOLD + "Repair" + GOLD + " >> " + YELLOW, "Repair is a skill focused on repairing items using an iron block."),
    SMITHING(5300, 1.205, 100, YELLOW.toString() + BOLD + "Smithing" + GOLD + " >> " + YELLOW, "Smithing is a skill focused on salvaging and smelting items.");

    private final int firstLevelXP;
    private final double xpLevelIncrement;
    private final int maxLevel;
    private final String prefix;
    private final String description;

    Skills(int xp, double increment, int maxLevel, String prefix, String desc) {
        firstLevelXP = xp;
        xpLevelIncrement = increment;
        this.maxLevel = maxLevel;
        this.prefix = prefix;
        description = desc;
    }

    public int getFirstLevelXP() {
        return firstLevelXP;
    }

    public double getXpLevelIncrement() {
        return xpLevelIncrement;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public static Skills get(String name) {
        for (Skills s : values()) {
            if (s.toString().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public static int getTotal() {
        int tot = 0;
        for (int i = 0; i < values().length; i++) {
            tot++;
        }
        return tot;
    }

    public static boolean isSkill(String label) {
        for (Skills s : Skills.values()) {
            if (label.equalsIgnoreCase(s.name())) return true;
        }
        return false;
    }
}
