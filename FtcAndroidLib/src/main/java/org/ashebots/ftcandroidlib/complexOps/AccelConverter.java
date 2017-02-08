package org.ashebots.ftcandroidlib.complexOps;

public class AccelConverter {
    long lastTime;
    double speed = 0;
    double distance = 0;
    public double parseAccel(double accel) {
        if (lastTime == 0) {
            lastTime = System.currentTimeMillis();
            return 0;
        }
        long elapsedTime = (System.currentTimeMillis() - lastTime) / 1000;
        lastTime = System.currentTimeMillis();
        speed += accel / elapsedTime;
        distance += speed / elapsedTime;
        return distance;
    }
}
