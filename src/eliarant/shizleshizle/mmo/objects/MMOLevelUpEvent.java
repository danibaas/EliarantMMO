package eliarant.shizleshizle.mmo.objects;

import eliarant.shizleshizle.mmo.utils.Skills;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MMOLevelUpEvent extends Event {
    private final String playerName;
    private final Skills skillType;
    private final int newLevel;

    public MMOLevelUpEvent(String playerName, Skills skillType, int newLevel) {
        this.playerName = playerName;
        this.skillType = skillType;
        this.newLevel = newLevel;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Skills getSkill() {
        return skillType;
    }

    public int getCurrentLevel() {
        return newLevel;
    }
}
