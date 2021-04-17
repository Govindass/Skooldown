package net.govindas.skooldown.utilities;

import net.govindas.skooldown.Skooldown;
import org.bukkit.Bukkit;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class CleanupTimer {
    //Regular Cleanup Timer to prevent memory leaks
    public CleanupTimer() {}
    public Timer start() {
        Timer timer = new Timer();
        TimerTask regularTask = new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                Iterator<Map.Entry<String, Long>> it = Skooldown.cooldowns.entrySet().iterator();
                i = 0;
                while (it.hasNext()) {
                    Map.Entry<String, Long> pair = it.next();

                    long value = pair.getValue();
                    if (value < System.currentTimeMillis()) {
                        i++;

                        it.remove();
                    }
                }
                if (i > 0) {
                    Bukkit.getLogger().log(Level.INFO, "[Skooldown Regular Memory Cleanup] " + i + " finished cooldowns cleared from memory.");
                }
            }

        };
        //regular schedule
        int minutes = 30;
        timer.schedule(regularTask, 100, 1000 * 60 * minutes);
        return timer;
    }
}
