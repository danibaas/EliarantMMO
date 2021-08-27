package eliarant.shizleshizle.mmo.objects;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class KnockbackHelper {

    public static void knockback(@NotNull Entity t, @NotNull Entity knockbackFrom, int multiplier) {
        t.setVelocity(knockbackFrom.getLocation().getDirection().setY(0).normalize().multiply(multiplier));
    }
}
