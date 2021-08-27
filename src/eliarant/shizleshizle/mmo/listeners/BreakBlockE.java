package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.objects.skills.Excavation;
import eliarant.shizleshizle.mmo.objects.skills.Herbalism;
import eliarant.shizleshizle.mmo.objects.skills.Mining;
import eliarant.shizleshizle.mmo.objects.skills.Woodcutting;
import me.shizleshizle.core.commands.Smelt;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class BreakBlockE implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        User p = new User(e.getPlayer());
        if (p.getGameMode() != GameMode.SURVIVAL) return;
        Block b = e.getBlock();
        if (e.getBlock() instanceof Ageable) {
            final Ageable age = (Ageable) b.getBlockData();
            Herbalism herb = new Herbalism(p.getName());
            if (herb.isMineableBlock(b)) {
                final double xp = herb.getXPForBlock(b);
                final double multiplier = herb.getMultiplier(p.getItemInMainHand());
                if (herb.isFarmlandCrop(b)) {
                    if (age.getAge() == age.getMaximumAge()) {
                        final double replantChance = herb.getReplantChance();
                        final int rando = Numbers.getRandom(0, herb.MaxPercentage);
                        if (replantChance == herb.MaxPercentage || Numbers.approximatelyEqual(replantChance, rando, MMO.Standard_Deviation)) {
                            final Collection<ItemStack> drops = b.getDrops();
                            for (ItemStack is : drops) {
                                p.getWorld().dropItem(b.getLocation(), is);
                            }
                            herb.replant(b);
                        }
                    }
                }
                herb.addXP((int) Math.round(xp * multiplier));
                herb.saveToFile();
            }
        }
        Excavation exca = new Excavation(p.getName());
        if (exca.isMineableBlock(b)) {
            final double xp = exca.getXPForBlock(b);
            final double finalXP = Math.round(xp * exca.getMultiplier(p.getItemInMainHand()));
            final int toAdd = (int) finalXP;
            if (xp > 0) {
                exca.addXP(toAdd);
                final ItemStack dropItem = exca.getDrop();
                if (dropItem != null) p.getWorld().dropItem(b.getLocation(), dropItem);
                exca.saveToFile();
            }
        }
        Mining min = new Mining(p.getName());
        if (min.isMineableBlock(b)) {
            min.addXP(min.getXPForBlock(b));
            double smeltChance = min.getSmeltChance();
            int rando = Numbers.getRandom(0, min.MaxPercentage);
            if (smeltChance == min.MaxPercentage || Numbers.approximatelyEqual(smeltChance, rando, MMO.Standard_Deviation)) {
                ArrayList<ItemStack> drops = new ArrayList<>();
                for (ItemStack item : b.getDrops()) {
                    if (item != null) {
                        ItemStack im = Smelt.smeltOre(item);
                        if (im != null) drops.add(im);
                    }
                }
                b.getDrops().clear();
                b.getDrops().addAll(drops);
            }
            min.saveToFile();
        }
        Woodcutting wood = new Woodcutting(p.getName());
        if (wood.isMineableBlock(b)) {
            wood.addXP(wood.getXPForBlock(b));
            final double doubleDrop = wood.getDoubleDropChance();
            final int rando = Numbers.getRandom(0, wood.MaxPercentage);
            if (doubleDrop == wood.MaxPercentage || Numbers.approximatelyEqual(doubleDrop, rando, MMO.Standard_Deviation)) {
                Collection<ItemStack> drops = b.getDrops();
                b.getDrops().addAll(drops);
            }
            wood.saveToFile();
        }
    }
}
