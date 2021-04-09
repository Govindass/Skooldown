package net.govindas.skooldown.expressions;


import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;

public class ExprCooldown extends SimpleExpression<Timespan> {

    private Expression<String> name;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) { return "get cooldown " + name.toString(event, debug); }

    @Override
    protected Timespan[] get(Event e) {
        Long cooldown;
        cooldown = Skooldown.cooldowns.get(name.getSingle(e));

        //if cooldown isn't created, will return that it is over

        if (cooldown == null) return new Timespan[]{new Timespan(0)};

        //if cooldown has expired, will return 0ms

        if (cooldown < System.currentTimeMillis()) {
            Skooldown.cooldowns.remove(name.getSingle(e));
            return new Timespan[]{new Timespan(0)};
        }
        //return time left
        return new Timespan[]{new Timespan(cooldown - System.currentTimeMillis())};
    }

    @Override
    public void change(Event e, Object[] changer, ChangeMode mode) {
        switch (mode) {
            case SET:
                Skooldown.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + ((Timespan) changer[0]).getMilliSeconds());
                break;
            case REMOVE_ALL:
            case DELETE:
            case RESET:

                Skooldown.cooldowns.remove(name.getSingle(e));
                break;
            case ADD:
                Long cooldown = Skooldown.cooldowns.get(name.getSingle(e));
                if (cooldown == null) {
                    Skooldown.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + ((Timespan) changer[0]).getMilliSeconds());
                    break;
                }
                Skooldown.cooldowns.put(name.getSingle(e), cooldown + ((Timespan) changer[0]).getMilliSeconds());
                break;
            case REMOVE:
                cooldown = Skooldown.cooldowns.get(name.getSingle(e));
                //if removing from non-existent cooldown, do nothing
                if (cooldown == null) return;

                //remove cooldown from hashMap if it would expire with new value

                if ((cooldown - ((Timespan) changer[0]).getMilliSeconds()) < System.currentTimeMillis()) {
                    Skooldown.cooldowns.remove(name.getSingle(e));
                } else {
                    Skooldown.cooldowns.put(name.getSingle(e), cooldown - ((Timespan) changer[0]).getMilliSeconds());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
        }


    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) { return CollectionUtils.array(Timespan.class); }
}

