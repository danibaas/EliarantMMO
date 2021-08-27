package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Excavation extends BlockBreakingSkill {

    public Excavation(String owner) {
        super(owner, Skills.EXCAVATION);
        addDrop(new ItemStack(Material.DIAMOND), 0.001);
        addDrop(new ItemStack(Material.IRON_INGOT), 0.012);
        addDrop(new ItemStack(Material.NETHER_WART), 0.007);
        addDrop(new ItemStack(Material.GOLD_INGOT), 0.006);
        addDrop(new ItemStack(Material.GLOWSTONE_DUST), 0.0033);
        addDrop(new ItemStack(Material.STICK), 0.05);
        addDrop(new ItemStack(Material.FLINT), 0.036);
        mineables.put(Material.CLAY, 90);
        mineables.put(Material.DIRT, 70);
        mineables.put(Material.GRASS_BLOCK, 70);
        mineables.put(Material.SAND, 70);
        mineables.put(Material.SNOW_BLOCK, 70);
        mineables.put(Material.SOUL_SOIL, 70);
        mineables.put(Material.COARSE_DIRT, 75);
        mineables.put(Material.ROOTED_DIRT, 75);
        mineables.put(Material.RED_SAND, 75);
        mineables.put(Material.SNOW, 75);
        mineables.put(Material.MYCELIUM, 80);
        mineables.put(Material.SOUL_SAND, 65);
    }
}
