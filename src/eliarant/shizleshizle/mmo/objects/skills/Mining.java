package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class Mining extends BlockBreakingSkill {
    private HashMap<Material, Integer> mineables = new HashMap<>();

    public Mining(String owner) {
        super(owner, Skills.MINING);
        for (Material mat : Material.values()) {
            if (mat.name().toLowerCase().contains("terracotta")) {
                mineables.put(mat, 50);
            }
        }
        mineables.put(Material.MOSSY_COBBLESTONE, 30);
        mineables.put(Material.NETHERRACK, 30);
        mineables.put(Material.SANDSTONE, 30);
        mineables.put(Material.STONE, 30);
        mineables.put(Material.PACKED_ICE, 50);
        mineables.put(Material.PRISMARINE, 70);
        mineables.put(Material.COAL_ORE, 100);
        mineables.put(Material.NETHER_QUARTZ_ORE, 100);
        mineables.put(Material.REDSTONE_ORE, 150);
        mineables.put(Material.END_STONE, 150);
        mineables.put(Material.OBSIDIAN, 150);
        mineables.put(Material.PURPUR_BLOCK, 200);
        mineables.put(Material.IRON_ORE, 250);
        mineables.put(Material.GOLD_ORE, 350);
        mineables.put(Material.LAPIS_ORE, 400);
        mineables.put(Material.DIAMOND_ORE, 750);
        mineables.put(Material.EMERALD_ORE, 1000);
    }

    public double getSmeltChance() {
        return getCurrentLevel() / 100d;
    }
}
