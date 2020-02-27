package lt.govindas.skooldown.expressions;


import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import lt.govindas.skooldown.utilities.Timer;

//TODO add event cooldown editing support, such as remove/add time
public class ExprCooldown extends SimpleExpression<Timespan> {

    private Expression<String> name;
    private Expression<String> data;
    private boolean eventCooldown = false;
    private String dataInput = "";

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
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        int mark = paramParseResult.mark;
        if (mark == 1) { eventCooldown = true; }

        if (expr.length > 2) { data = (Expression<String>) expr[1]; }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "get cooldown ";
    }

    @Override
    protected Timespan[] get(Event e) {
        if (data != null) dataInput = data.getSingle(e);
        Long cooldown;
        Timer timerCooldown;
        if (!eventCooldown) {
            cooldown = Skooldown.cooldowns.get(name.getSingle(e) + dataInput);

            //if cooldown isn't created, will return that it is over

            if (cooldown == null) return new Timespan[]{new Timespan(0)};

            //if cooldown has expired, will return 0ms

            if (cooldown < System.currentTimeMillis()) {
                Skooldown.cooldowns.remove(name.getSingle(e) + dataInput);
                return new Timespan[]{new Timespan(0)};
            }
            //return time left
            return new Timespan[]{new Timespan(cooldown - System.currentTimeMillis())};
        } else {
            timerCooldown = Skooldown.eventCooldowns.get(name.getSingle(e) + dataInput);

            //if cooldown isn't created, will return that it is over

            if (timerCooldown == null) return new Timespan[]{new Timespan(0)};
            //return time left

            return new Timespan[] {new Timespan(timerCooldown.getEndDate() - System.currentTimeMillis())};
        }
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
                if (data != null) dataInput = data.getSingle(e);

                if (!eventCooldown) { Skooldown.cooldowns.remove(name.getSingle(e)); }
                else {
                    Timer timer = Skooldown.eventCooldowns.get(name.getSingle(e) + dataInput);

                    if (timer != null) {
                        timer.stop();
                        Skooldown.eventCooldowns.remove(name.getSingle(e) + dataInput);
                    }
                }
                break;
            case ADD:
                if (!eventCooldown) {
                    Long cooldown = Skooldown.cooldowns.get(name.getSingle(e));
                    if (cooldown == null) {
                        Skooldown.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + ((Timespan) changer[0]).getMilliSeconds());
                        break;
                    }
                    Skooldown.cooldowns.put(name.getSingle(e), cooldown + ((Timespan) changer[0]).getMilliSeconds());
                    break;
                } else {
                  //todo make this stop the timer without calling event & start it with another time fitting to time elapsed and added time
                }
            case REMOVE:
                Long cooldown = Skooldown.cooldowns.get(name.getSingle(e));
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

