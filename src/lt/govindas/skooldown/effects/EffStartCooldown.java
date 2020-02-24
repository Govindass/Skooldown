package lt.govindas.skooldown.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import lt.govindas.skooldown.Skooldown;
import lt.govindas.skooldown.events.CooldownEndEvent;
import lt.govindas.skooldown.utilities.Timer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffStartCooldown extends Effect {

    private Expression<String> name;
    private Expression<Timespan> time;
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
        //TODO test if this is right, maybe expression IDs are solid
        if (expr.length > 2) {
            data = (Expression<String>) expr[1];
            time = (Expression<Timespan>) expr[2];
        } else {
            time = (Expression<Timespan>) expr[1];
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "start cooldown " + name.getSingle(e);
    }

    @Override
    protected void execute(Event e) {



        if (!eventCooldown) {
            Skooldown.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + time.getSingle(e).getMilliSeconds());
        } else {
            if (data != null) dataInput = data.getSingle(e);
            Timer timer = new Timer((int) time.getSingle(e).getMilliSeconds(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Skooldown.eventCooldowns.remove(name.getSingle(e) + dataInput);
                    Bukkit.getServer().getPluginManager().callEvent(new CooldownEndEvent(name.getSingle(e), dataInput, time.getSingle(e).getMilliSeconds()));
                }
            });
            timer.setRepeats(false);
            timer.start();
            Skooldown.eventCooldowns.put(name.getSingle(e) + dataInput, timer);
        }
    }
}
