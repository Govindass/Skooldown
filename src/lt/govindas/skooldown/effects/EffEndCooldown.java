package lt.govindas.skooldown.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;

public class EffEndCooldown extends Effect {

    private Expression<String> name;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
        name = (Expression<String>) expr[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "end cooldown  " + name.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        Skooldown.cooldowns.remove(name.getSingle(e));
    }
}

