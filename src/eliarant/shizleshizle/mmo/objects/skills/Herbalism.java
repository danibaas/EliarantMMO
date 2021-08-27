package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.jetbrains.annotations.NotNull;

public class Herbalism extends BlockBreakingSkill {

    // replanting
    private final double Max_Replant_Chance = 0.75;
    private double replantChance = 0.05;

    public Herbalism(@NotNull String owner) {
        super(owner, Skills.HERBALISM);
        for (Material mat : Material.values()) {
            String name = mat.name().toLowerCase();
            if (name.contains("coral") && name.contains("dead")) {
                mineables.put(mat, 10);
            } else if (name.contains("coral")) {
                if (name.contains("tube")) {
                    mineables.put(mat, 80);
                } else if (name.contains("brain")) {
                    mineables.put(mat, 90);
                } else if (name.contains("fire")) {
                    mineables.put(mat, 120);
                } else if (name.contains("horn")) {
                    mineables.put(mat, 175);
                } else if (name.contains("bubble")) {
                    mineables.put(mat, 75);
                }
            }
            if (name.contains("tulip") || name.contains("mushroom")) {
                mineables.put(mat, 150);
            }
        }
        mineables.put(Material.KELP, 3);
        mineables.put(Material.SEAGRASS, 10);
        mineables.put(Material.VINE, 10);
        mineables.put(Material.BAMBOO, 10);
        mineables.put(Material.GRASS, 10);
        mineables.put(Material.FERN, 10);
        mineables.put(Material.MELON, 20);
        mineables.put(Material.PUMPKIN, 20);
        mineables.put(Material.COCOA_BEANS, 30);
        mineables.put(Material.DEAD_BUSH, 30);
        mineables.put(Material.SUGAR_CANE, 30);
        mineables.put(Material.CACTUS, 30);
        mineables.put(Material.LILAC, 50);
        mineables.put(Material.BEETROOTS, 50);
        mineables.put(Material.POTATOES, 50);
        mineables.put(Material.NETHER_WART, 50);
        mineables.put(Material.CARROTS, 50);
        mineables.put(Material.WHEAT, 50);
        mineables.put(Material.LARGE_FERN, 50);
        mineables.put(Material.TALL_GRASS, 50);
        mineables.put(Material.PEONY, 50);
        mineables.put(Material.ROSE_BUSH, 50);
        mineables.put(Material.POPPY, 100);
        mineables.put(Material.LILY_PAD, 100);
        mineables.put(Material.DANDELION, 100);
        mineables.put(Material.AZURE_BLUET, 150);
        mineables.put(Material.BLUE_ORCHID, 150);
        mineables.put(Material.CORNFLOWER, 150);
        mineables.put(Material.OXEYE_DAISY, 150);
        mineables.put(Material.LILY_OF_THE_VALLEY, 150);
        mineables.put(Material.ALLIUM, 300);
        mineables.put(Material.SWEET_BERRY_BUSH, 300);
        mineables.put(Material.WITHER_ROSE, 500);
    }

    public void replant(Block b) {
        if (b instanceof Ageable) {
            Ageable age = (Ageable) b;
            age.setAge(0);
        }
    }

    public double getReplantChance() {
        replantChance = getCurrentLevel() / 100d;
        if (replantChance > Max_Replant_Chance) replantChance = Max_Replant_Chance;
        return replantChance;
    }

    public boolean isFarmlandCrop(Block b) {
        return switch (b.getType()) {
            case WHEAT, POTATOES, CARROTS, BEETROOTS, NETHER_WART -> true;
            default -> false;
        };
    }
}
