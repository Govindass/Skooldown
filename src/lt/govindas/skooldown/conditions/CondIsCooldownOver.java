package lt.govindas.skooldown.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondIsCooldownOver extends Condition {

    private Expression<String> name;
    private Expression<String> data;
    private boolean eventCooldown = false;
    private String dataInput = "";

    @SuppressWarnings("unchecked")
    @Override

    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        int mark = paramParseResult.mark;
        if (mark == 1) eventCooldown = true;
        if (expr.length > 1) data = (Expression<String>) expr[1];
        setNegated(matchedPattern == 2 || matchedPattern == 3);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "is cooldown over " + name;
    }

    @Override
    public boolean check(Event e) {
        if (!eventCooldown) {
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
        } else {
            if (data != null) dataInput = data.getSingle(e);

            if (Skooldown.eventCooldowns.containsKey(name.getSingle(e) + dataInput)) return isNegated();
            else return !isNegated();
        }
    }
}