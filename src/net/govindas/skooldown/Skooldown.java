package net.govindas.skooldown;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Timespan;
import net.govindas.skooldown.conditions.CondIsCooldownOver;
import net.govindas.skooldown.effects.EffEndCooldown;
import net.govindas.skooldown.effects.EffStartCooldown;
import net.govindas.skooldown.expressions.ExprCooldown;
import net.govindas.skooldown.utilities.CleanupTimer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentHashMap;

public final class Skooldown extends JavaPlugin {
    public static ConcurrentHashMap<String, Long> cooldowns = new ConcurrentHashMap<String, Long>();

    @Override
    public void onEnable() {
        Skript.registerAddon(this);
        Skript.registerEffect(EffStartCooldown.class, "(create|start) [a] cooldown %string% for %timespan%", "(create|start) [a] cooldown %string% for %timespan%");


        Skript.registerEffect(EffEndCooldown.class, "(reset|stop|delete|clear) cooldown %string%");

        Skript.registerCondition(CondIsCooldownOver.class, "cooldown %string% (is|has) (finished|over|done)", "cooldown %string% is(n't| not) unfinished)", "cooldown %string% is(n't| not) (finished|over|done)", "cooldown %string% is unfinished");
        Skript.registerExpression(ExprCooldown.class, Timespan.class, ExpressionType.PROPERTY, "cooldown %string%");

        getLogger().info("Skript addon enabled!");
        new CleanupTimer();
    }

    @Override
    public void onDisable() {
        cooldowns = null;
        getLogger().info("Skript addon disabled!");
    }
}
