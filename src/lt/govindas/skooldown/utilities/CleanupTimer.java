package lt.govindas.skooldown.utilities;

import lt.govindas.skooldown.Skooldown;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CleanupTimer {
    //Hourly Timer to prevent memory leaks
    public CleanupTimer() {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                Iterator it = Skooldown.cooldowns.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    long value = (long) pair.getValue();
                    if (value < System.currentTimeMillis()) {
                        i++;

                        it.remove();
                    }
                }
                if (i > 0) {
                    System.out.println("[Skooldown Hourly Memory Cleanup] " + i + " finished cooldowns cleared from memory.");
                }
            }

        };
        //hourly schedule
        timer.schedule(hourlyTask, 100, 1000 * 60 * 60);
    }
}
