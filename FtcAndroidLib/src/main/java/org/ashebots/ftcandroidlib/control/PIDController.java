package org.ashebots.ftcandroidlib.control;


import android.os.SystemClock;

public class PIDController
{
    private PIDSettings settings; //Externally updatable Kp, Ki, & Kd terms

    private double timeOfLastCalc = 0; //Measured by System.uptimeMillis / 1000 (so, seconds..)

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
        //If calculate() hasn't been run in the last half second, make up a new time value for the last
        //iteration, so that the math doesn't jump around
        if (getRuntimeSeconds() - timeOfLastCalc > 0.5)
        {
            timeOfLastCalc = getRuntimeSeconds() - 0.01; //Set to some made up number, roughly one loop ago
        }

        double timeSinceLastCalc = getRuntimeSeconds() - timeOfLastCalc;

        //Bulk of PID algorithm:
        double error = targetValue - currentValue;
        integral += error * timeSinceLastCalc;
        double derivative = (error - lastError) / timeSinceLastCalc;

        if (Double.isNaN(derivative))
        {
            derivative = 0.0;
        }

        lastError = error;
        timeOfLastCalc = getRuntimeSeconds();

        //Add all the parts together & return
        return error * settings.getProportionalTerm()
                + integral * settings.getIntegralTerm()
                + derivative * settings.getDerivativeTerm();

        //Adapted from: http://robotsforroboticists.com/pid-control/
    }

    private double getRuntimeSeconds()
    {
        return ((double)SystemClock.uptimeMillis()) / 1000.0;
    }

    public PIDSettings getSettings()
    {
        return this.settings;
    }
}
