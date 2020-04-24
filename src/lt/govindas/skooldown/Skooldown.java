package lt.govindas.skooldown;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.applet2.AppletParameters;
import lt.govindas.skooldown.conditions.CondIsCooldownOver;
import lt.govindas.skooldown.effects.EffEndCooldown;
import lt.govindas.skooldown.effects.EffStartCooldown;
import lt.govindas.skooldown.events.CooldownEndEvent;
import lt.govindas.skooldown.events.EvtCooldown;
import lt.govindas.skooldown.expressions.ExprCooldown;
import lt.govindas.skooldown.expressions.ExprCooldownData;
import lt.govindas.skooldown.utilities.CleanupTimer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import lt.govindas.skooldown.utilities.Timer;
import java.util.HashMap;

public final class Skooldown extends JavaPlugin {
    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    public static HashMap<String, Timer> eventCooldowns = new HashMap<String, Timer>();


    @Override
    public void onEnable() {
        Skript.registerEffect(EffStartCooldown.class, "(create|start) [a] cooldown %string% for %timespan%", "(create|start) [a] (1¦event) cooldown %string% [with data %-string%] for %timespan%");


        Skript.registerEffect(EffEndCooldown.class, "(reset|stop|delete|clear) [(1¦event)] cooldown %string% [with data %-string%]");

        Skript.registerCondition(CondIsCooldownOver.class, "[(1¦event)] cooldown %string% [with data %-string%] (is|has) (finished|over|done)", "[the] [(1¦event)] cooldown %string% [with data %-string%] is(n't| not) unfinished)", "[the] [(1¦event)] cooldown %string% [with data %-string%] is(n't| not) (finished|over|done)", "[the] [(1¦event)] cooldown %string% [with data %-string%] is unfinished");
        Skript.registerExpression(ExprCooldown.class, Timespan.class, ExpressionType.PROPERTY, "[(1¦event)] cooldown %string% [with data %-string%]");

       Skript.registerEvent("Cooldown End", EvtCooldown.class, CooldownEndEvent.class, "(finish|end|complete) of cooldown %string%");
       Skript.registerExpression(ExprCooldownData.class, String.class, ExpressionType.SIMPLE, "cooldown data");
        EventValues.registerEventValue(CooldownEndEvent.class, String.class, new Getter<String, CooldownEndEvent>() {
            @Override
            public String get(CooldownEndEvent e) {
                return e.getData();
            }
        }, 0);
        getLogger().info("[Skooldown] Plugin enabled!");
        new CleanupTimer();
    }

    @Override
    public void onDisable() {
        getLogger().info("[Skooldown] Plugin disabled!");
    }
}
