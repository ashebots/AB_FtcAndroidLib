package org.ashebots.ftcandroidlib.control;


import android.os.SystemClock;

public class PIDController
{
    private PIDSettings settings; //Externally updatable Kp, Ki, & Kd terms

    private double timeOfLastCalc = 0; //Measured by System.nanoTime();

    private double lastError = 0;
    private double integral = 0;


    public PIDController(double proportionalTerm, double integralTerm, double derivativeTerm)
    {
        this(new PIDSettings(proportionalTerm, integralTerm, derivativeTerm));
    }

    public PIDController(PIDSettings settings)
    {
        this.settings = settings;
    }


    public double calculate(double currentValue, double targetValue)
    {
        double timeSinceLastCalc = SystemClock.uptimeMillis() - timeOfLastCalc;

        //Bulk of PID algorithm:
        double error = targetValue - currentValue;
        integral += error * timeSinceLastCalc;
        double derivative = (error - lastError) / timeSinceLastCalc;

        lastError = error;
        timeOfLastCalc = SystemClock.uptimeMillis();

        //Add all the parts together & return
        return error * settings.getProportionalTerm()
                + integral * settings.getIntegralTerm()
                + derivative * settings.getDerivativeTerm();

        //Adapted from: http://robotsforroboticists.com/pid-control/
    }


    public PIDSettings getSettings()
    {
        return this.settings;
    }
}
