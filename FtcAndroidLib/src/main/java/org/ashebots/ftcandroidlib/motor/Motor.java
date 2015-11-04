package org.ashebots.ftcandroidlib.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Motor extends DcMotor
{
    protected float gearRatio = 1.0f;
    protected float powerMultiplier = 1.0f;

    protected int encoderTicksPerRevolution; //1440 for Tetrix, 1120 for AndyMark. I think
    protected float distancePerRevolution; //If this motor is connected directly a wheel, it would be the wheel's circumference


    //
    // CONSTRUCTORS
    //
    public Motor(DcMotorController controller, int portNumber)
    {
        super(controller, portNumber);
    }

    public Motor(DcMotorController controller, int portNumber, DcMotor.Direction direction)
    {
        super(controller, portNumber, direction);
    }


    //
    // METHODS
    //
    public int calculateEncoderTicksForDistance(float distance)
    {
        float rotationsToTarget = distance / this.distancePerRevolution;
        float encoderTicksToTarget = rotationsToTarget * this.encoderTicksPerRevolution;
        encoderTicksToTarget *= this.gearRatio;
        return (int) encoderTicksToTarget;
    }



    //
    // GETTERS / SETTERS
    //

    //gearRatio
    public float getGearRatio()
    {
        return this.gearRatio;
    }
    public void setGearRatio(float ratio)
    {
        this.gearRatio = ratio;
    }
    public void setGearRatio(int inputTeeth, int outputTeeth)
    {
        this.gearRatio = outputTeeth / inputTeeth; //Did I get this right?
    }

    //encoderTicksPerRevolution
    public int getEncoderTicksPerRevolution()
    {
        return this.encoderTicksPerRevolution;
    }
    public void setEncoderTicksPerRevolution(int ticks)
    {
        this.encoderTicksPerRevolution = ticks;
    }

    //distancePerRevolution
    public float getDistancePerRevolution()
    {
        return this.distancePerRevolution;
    }
    public void setDistancePerRevolution(float distance)
    {
        this.distancePerRevolution = distance;
    }
}
