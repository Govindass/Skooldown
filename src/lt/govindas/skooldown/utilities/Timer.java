package lt.govindas.skooldown.utilities;

import java.awt.event.ActionListener;

public class Timer extends javax.swing.Timer {
    long startTime = System.currentTimeMillis();
    /**
     * Creates a {@code Timer} and initializes both the initial delay and
     * between-event delay to {@code delay} milliseconds. If {@code delay}
     * is less than or equal to zero, the timer fires as soon as it
     * is started. If <code>listener</code> is not <code>null</code>,
     * it's registered as an action listener on the timer.
     *
     * @param delay    milliseconds for the initial and between-event delay
     * @param listener an initial listener; can be <code>null</code>
     * @see #addActionListener
     * @see #setInitialDelay
     * @see #setRepeats
     */
    public Timer(int delay, ActionListener listener) {
        super(delay, listener);
    }
    public long getEndDate() {
        return startTime + this.getDelay();

    }
}
