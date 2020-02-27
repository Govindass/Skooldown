package lt.govindas.skooldown.expressions;


import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.events.CooldownEndEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCooldownData extends SimpleExpression<String> {
    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "cooldown data ";
    }

    @Override
    protected String[] get(Event e) {
        if (e instanceof CooldownEndEvent){
            return new String[]{((CooldownEndEvent) e).getData()};
        }
        return null;
    }


    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) { return null; }
}

