package lt.govindas.skooldown;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Timespan;
import lt.govindas.skooldown.conditions.CondIsCooldownOver;
import lt.govindas.skooldown.effects.EffStartCooldown;
import lt.govindas.skooldown.effects.EffStartEventCooldown;
import lt.govindas.skooldown.expressions.ExprCooldown;
import lt.govindas.skooldown.utilities.CleanupTimer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.util.HashMap;

public final class Skooldown extends JavaPlugin {
    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    public static HashMap<String, Timer> eventCooldowns = new HashMap<String, javax.swing.Timer>();



    @Override
    public void onEnable() {
        Skript.registerEffect(EffStartCooldown.class, "(create|start) [a] [(1¦event] cooldown %string% [with data %-string%] for %timespan%");
        Skript.registerCondition(CondIsCooldownOver.class, "[the] [(1¦event] cooldown %string% [with data %-string%] (is|has) (finished|over|done)", "[the] [(1¦event] cooldown %string% [with data %-string%] is(n't| not) unfinished)", "[the] [(1¦event] cooldown %string% [with data %-string%] is(n't| not) (finished|over|done)", "[the] [(1¦event] cooldown %string% [with data %-string%] is unfinished");
        Skript.registerExpression(ExprCooldown.class, Timespan.class, ExpressionType.PROPERTY, "cooldown %string%");
        getLogger().info("[Skooldown] Plugin enabled!");
        new CleanupTimer();
    }

    @Override
    public void onDisable() {
        getLogger().info("[Skooldown] Plugin disabled!");
    }
}
