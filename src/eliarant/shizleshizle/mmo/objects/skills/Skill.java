package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.MMOSkill;
import eliarant.shizleshizle.mmo.utils.Skills;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class Skill extends MMOSkill {
    public final int MaxPercentage = 100;
    // multipliers
    final double WOOD = 1.05;
    final double STONE = 1.1;
    final double GOLD = 1.2;
    final double IRON = 1.3;
    final double DIAMOND = 1.4;
    final double NETHERITE = 1.5;

    // drop chance
    ArrayList<ItemStack> dropItems = new ArrayList<>();
    HashMap<ItemStack, Double> dropChance = new HashMap<>();

    public Skill(@NotNull String owner, @NotNull Skills type) {
        super(owner, type);
    }

    @Nullable
    public ItemStack getDrop() {
        int pos = Numbers.getRandom(0, dropItems.size() - 1);
        ItemStack is = dropItems.get(pos);
        double dropChancePerItem = dropChance.get(is) * MaxPercentage;
        double multiplier = Math.round(getCurrentLevel() / 100.0);
        double finalDropChance = dropChancePerItem * multiplier;
        boolean canDrop = dropChancePerItem == MaxPercentage || Numbers.approximatelyEqual(dropChancePerItem, finalDropChance, 5);
        if (canDrop) return is;
        return null;
    }

    /**
     * Get the multiplier for the tool used.
     *
     * @param i The item held in hand which must be a tool.
     * @return Returns the multiplier according to the used tool, if the it is not a tool or not an item at all this function will return 1.
     */
    public double getMultiplier(ItemStack i) {
        return switch (i.getType()) {
            case WOODEN_SHOVEL, WOODEN_AXE, WOODEN_PICKAXE, WOODEN_SWORD, WOODEN_HOE -> WOOD;
            case STONE_SHOVEL, STONE_AXE, STONE_PICKAXE, STONE_SWORD, STONE_HOE -> STONE;
            case GOLDEN_SHOVEL, GOLDEN_AXE, GOLDEN_PICKAXE, GOLDEN_SWORD, GOLDEN_HOE -> GOLD;
            case IRON_SHOVEL, IRON_AXE, IRON_PICKAXE, IRON_SWORD, IRON_HOE -> IRON;
            case DIAMOND_SHOVEL, DIAMOND_AXE, DIAMOND_PICKAXE, DIAMOND_SWORD, DIAMOND_HOE -> DIAMOND;
            case NETHERITE_SHOVEL, NETHERITE_AXE, NETHERITE_PICKAXE, NETHERITE_SWORD, NETHERITE_HOE -> NETHERITE;
            default -> 1;
        };
    }

    protected void addDrop(ItemStack i, double chance) {
        dropChance.put(i, chance);
        dropItems.add(i);
    }
}
