package eliarant.shizleshizle.mmo.objects.skills;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BlockBreakingSkill extends Skill {
    protected HashMap<Material, Integer> mineables = new HashMap<>();

    public BlockBreakingSkill(@NotNull String owner, @NotNull Skills type) {
        super(owner, type);
    }

    public boolean isMineableBlock(Block b) {
        return mineables.containsKey(b.getType());
    }

    public int getXPForBlock(Block b) {
        return mineables.get(b.getType());
    }
}
