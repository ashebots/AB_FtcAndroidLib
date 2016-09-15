package org.ashebots.ftcandroidlib.complexOps;

/**
 * Created by apple on 9/15/16.
 */
public class Scaler {
    protected int ticksPer = 0;
    protected double gearRatio = 0;

    //encoderTicksPerRevolution
    public int getTicksPer()
    {
        return ticksPer;
    }
    public void setTicksPer(int ticks)
    {
        ticksPer = ticks;
    }
    public void addGearRatio(int inputTeeth, int outputTeeth) {
        gearRatio *= outputTeeth / inputTeeth;
    }
    public double getGearRatio() {
        return gearRatio;
    }

    public double s(double scale) {
        return scale*ticksPer*gearRatio;
    }
}
