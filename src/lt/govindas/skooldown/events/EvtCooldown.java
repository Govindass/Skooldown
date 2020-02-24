package lt.govindas.skooldown.events;

import ch.njol.skript.lang.Expression;
import lt.govindas.skooldown.Skooldown;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EvtCooldown extends SkriptEvent {

    String name;

    @Override
    public boolean init(Literal<?>[] lit, int arg1, ParseResult arg2) {
        name = Arrays.toString(lit);
        name = name.substring(1, name.length()-1);
        return true;
    }
    @Override
    public String toString(@Nullable Event event, boolean arg1) {
        return "cooldown end event ";
    }

    @Override
    public boolean check(Event event) {
        if (event instanceof CooldownEndEvent) {
            return ((CooldownEndEvent) event).getName().equalsIgnoreCase(name);
        }
        return false;
    }



}