package org.ashebots.ftcandroidlib.control;


public class PIDController
{
    private PIDSettings settings;

    private double prior_error = 0;
    private double integral = 0;


    public PIDController(double proportionalTerm, double integralTerm, double derivativeTerm)
    {
        this(new PIDSettings(proportionalTerm, integralTerm, derivativeTerm));
    }

    public PIDController(PIDSettings settings)
    {
        this.settings = settings;
    }

    public double Calculate(double currentValue, double targetValue)
    {
        double timeSinceLastCalc = 0; //THIS NEEDS AN ACTUAL VALUE!!

        double error = targetValue - currentValue;
        //http://robotsforroboticists.com/pid-control/

        return 0; //THIS NEEDS AN ACTUAL VALUE !!
    }




}
