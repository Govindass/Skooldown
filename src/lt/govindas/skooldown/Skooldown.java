package lt.govindas.skooldown;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Timespan;
import lt.govindas.skooldown.conditions.CondCooldownOver;
import lt.govindas.skooldown.effects.EffStartCooldown;
import lt.govindas.skooldown.expressions.ExprCooldown;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Skooldown extends JavaPlugin {
    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();



    @Override
    public void onEnable() {
        Skript.registerEffect(EffStartCooldown.class, "(create|start) [a] cooldown %string% for %timespan%");
        Skript.registerCondition(CondCooldownOver.class, "[the] cooldown %string% (is|has) (finished|over|done)", "[the] cooldown %string% is(n't| not) unfinished)", "[the] cooldown %string% is(n't| not) (finished|over|done)", "[the] cooldown %string% is unfinished");
        Skript.registerExpression(ExprCooldown.class, Timespan.class, ExpressionType.PROPERTY, "cooldown %string%");
        getLogger().info("[Skooldown] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("[Skooldown] Plugin disabled!");
    }
}
