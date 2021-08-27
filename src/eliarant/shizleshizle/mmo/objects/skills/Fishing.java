package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Fishing extends Skill {
    // cooking fish
    private final int Minimum_Cook_Level = 15;
    private final double CookIncrement = 1.2;

    public Fishing(@NotNull String owner) {
        super(owner, Skills.FISHING);
        addDrop(new ItemStack(Material.DIAMOND), 0.0025);
        addDrop(new ItemStack(Material.IRON_INGOT), 0.0075);
        addDrop(new ItemStack(Material.ENDER_PEARL), 0.05);
        addDrop(new ItemStack(Material.TNT), 0.15);
        addDrop(new ItemStack(Material.ELYTRA), 0.000005);
    }

    public int getXPForItem(@NotNull ItemStack caught) {
        return switch (caught.getType()) {
            case SALMON, COD, TROPICAL_FISH, PUFFERFISH -> 75;
            case BOW, ENCHANTED_BOOK, FISHING_ROD, NAME_TAG, NAUTILUS_SHELL, SADDLE -> 60;
            default -> 40;
        };
    }

    public double getCookChance() {
        if (getCurrentLevel() < Minimum_Cook_Level) return 0;
        return CookIncrement * (getCurrentLevel() - Minimum_Cook_Level);
    }

    public boolean isCookable(@NotNull ItemStack is) {
        return (is.getType() == Material.COD || is.getType() == Material.SALMON);
    }

    public ItemStack getCooked(@NotNull ItemStack is) {
        if (is.getType() == Material.COD) is.setType(Material.COOKED_COD);
        if (is.getType() == Material.SALMON) is.setType(Material.COOKED_SALMON);
        return is;
    }
}
