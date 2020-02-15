package lt.govindas.skooldown.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondCooldownOver extends Condition {

    private Expression<String> name;

    @SuppressWarnings("unchecked")
    @Override

    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        System.out.println(matchedPattern);
        setNegated(matchedPattern == 2 || matchedPattern == 3);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "is cooldown over" + name;
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