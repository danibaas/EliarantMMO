package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class Woodcutting extends BlockBreakingSkill {

    public Woodcutting(@NotNull String owner) {
        super(owner, Skills.WOODCUTTING);
        mineables.put(Material.ACACIA_WOOD, 90);
        mineables.put(Material.BIRCH_WOOD, 90);
        mineables.put(Material.DARK_OAK_WOOD, 90);
        mineables.put(Material.JUNGLE_WOOD, 100);
        mineables.put(Material.OAK_WOOD, 70);
        mineables.put(Material.SPRUCE_WOOD, 80);
    }

    public double getDoubleDropChance() {
        return getCurrentLevel() / 100d;
    }
}
