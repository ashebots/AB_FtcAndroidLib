package org.ashebots.ftcandroidlib.complexOps;

/**
 * Created by Art Schell on 3/17/2016.
 */
public class Timer extends HardwareComponent {
    double oldT = 0;

    //if the timer has passed a value.

    public boolean tRange (double range) {
        return (System.currentTimeMillis() - oldT > range);
    }

    //resets the timer

    public void resetTimer() {
        oldT = System.currentTimeMillis();
    }
}
