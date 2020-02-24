package lt.govindas.skooldown.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import lt.govindas.skooldown.utilities.Timer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffEndCooldown extends Effect {

    private Expression<String> name;
    private Expression<String> data;
    private boolean eventCooldown = false;
    private String dataInput = "";

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        int mark = paramParseResult.mark;
        if (mark == 1) {
            eventCooldown = true;
        }
        if (expr.length > 2) {
            data = (Expression<String>) expr[1];
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "end cooldown  " + name.getSingle(e);
    }

    @Override
    protected void execute(Event e) {

        if (!eventCooldown) { Skooldown.cooldowns.remove(name.getSingle(e)); }
        else {
            if (data != null) dataInput = data.getSingle(e);
            Timer timer = Skooldown.eventCooldowns.get(name.getSingle(e) + dataInput);

            if (timer != null) {
                timer.stop();
                Skooldown.eventCooldowns.remove(name.getSingle(e) + dataInput);
            }
        }
    }
}
