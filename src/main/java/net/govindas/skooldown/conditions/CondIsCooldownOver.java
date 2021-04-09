package net.govindas.skooldown.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;

public class CondIsCooldownOver extends Condition {

    private Expression<String> name;

    @SuppressWarnings("unchecked")
    @Override

    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
        name = (Expression<String>) expr[0];
        setNegated(matchedPattern == 2 || matchedPattern == 3);
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "is cooldown over " + name.toString(event, debug);
    }

    @Override
    public boolean check(Event e) {
        Long cooldown = Skooldown.cooldowns.get(name.getSingle(e));

        //if cooldown isn't created, will return that it is over
        if (cooldown == null) return !isNegated();

        if (cooldown < System.currentTimeMillis()) {
            Skooldown.cooldowns.remove(name.getSingle(e));
            //will return that cooldown is over
            return !isNegated();
        }
        //will return that cooldown is not over

        return isNegated();
    }
}