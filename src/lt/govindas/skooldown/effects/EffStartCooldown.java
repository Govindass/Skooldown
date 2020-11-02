package lt.govindas.skooldown.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;

public class EffStartCooldown extends Effect {

    private Expression<String> name;
    private Expression<Timespan> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
        name = (Expression<String>) expr[0];
        time = (Expression<Timespan>) expr[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "start cooldown " + name.toString(event, debug) + " for " + time.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        Skooldown.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + time.getSingle(e).getMilliSeconds());
    }
}
