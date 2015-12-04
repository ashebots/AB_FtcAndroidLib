package org.ashebots.ftcandroidlib.control;


public class PIDSettings
{
    private double proportionalTerm; //Kp
    private double integralTerm;     //Ki
    private double derivativeTerm;   //Kd


    public PIDSettings(double proportionalTerm, double integralTerm, double derivativeTerm)
    {
        this.proportionalTerm = proportionalTerm;
        this.integralTerm = integralTerm;
        this.derivativeTerm = derivativeTerm;
    }


    public double getProportionalTerm() {
        return proportionalTerm;
    }

    public void setProportionalTerm(double proportionalTerm) {
        this.proportionalTerm = proportionalTerm;
    }

    public double getIntegralTerm() {
        return integralTerm;
    }

    public void setIntegralTerm(double integralTerm) {
        this.integralTerm = integralTerm;
    }

    public double getDerivativeTerm() {
        return derivativeTerm;
    }

    public void setDerivativeTerm(double derivativeTerm) {
        this.derivativeTerm = derivativeTerm;
    }
}
