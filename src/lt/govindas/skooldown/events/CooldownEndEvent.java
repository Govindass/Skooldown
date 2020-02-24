package lt.govindas.skooldown.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CooldownEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String data;
    private String name;
    private long delay;

    public CooldownEndEvent(String name, String data, long delay) {
        this.data = data;
        this.name = name;
        this.delay = delay;
    }

    public String getData() { return data; }
    public String getName() { return name;}

    public long getDelay() { return delay;}

    public HandlerList getHandlers() {
        return handlers;
    }
    public boolean matches(String id) {
        return id.equalsIgnoreCase(this.getName() + this.getData());
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

