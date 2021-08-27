package eliarant.shizleshizle.mmo.listeners;

import eliarant.shizleshizle.mmo.MMO;
import eliarant.shizleshizle.mmo.objects.skills.Fishing;
import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftItem;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishE implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        User p = new User(e.getPlayer());
        CraftItem item = (CraftItem) e.getCaught();
        if (item == null) return;
        ItemStack is = item.getItemStack();
        Fishing fish = new Fishing(e.getPlayer().getName());
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH || e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            fish.addXP(fish.getXPForItem(is));
            ItemStack drop = fish.getDrop();
            if (drop != null) {
                if (e.getCaught().getType() == EntityType.DROPPED_ITEM) {
                    Item t = (Item) e.getCaught();
                    t.setItemStack(drop);
                    fish.saveToFile();
                    return;
                }
            }
            if (fish.isCookable(is)) {
                double canCook = fish.getCookChance();
                int rando = Numbers.getRandom(0, fish.MaxPercentage);
                if (canCook == fish.MaxPercentage || Numbers.approximatelyEqual(canCook, rando, MMO.Standard_Deviation)) {
                    int amount = is.getAmount();
                    ItemStack cooked = fish.getCooked(is);
                    cooked.setAmount(amount);
                    p.getInventory().remove(is);
                    p.getInventory().addItem(cooked);
                    p.updateInventory();
                }
            }
            fish.saveToFile();
        }
    }
}
