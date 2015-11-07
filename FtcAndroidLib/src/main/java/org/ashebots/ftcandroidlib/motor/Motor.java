package org.ashebots.ftcandroidlib.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Motor extends DcMotor
{
    protected float gearRatio = 1.0f;
    protected float powerMultiplier = 1.0f;

    protected int encoderTicksPerRevolution; //1440 for Tetrix, 1120 for AndyMark. I think


    //
    // CONSTRUCTORS
    //

    //Copy an existing DcMotor
    public Motor(DcMotor existingMotor)
    {
        this(existingMotor.getController(), existingMotor.getPortNumber(), existingMotor.getDirection());
    }

    public Motor(DcMotorController controller, int portNumber)
    {
        this(controller, portNumber, Direction.FORWARD);
    }

    public Motor(DcMotorController controller, int portNumber, DcMotor.Direction direction)
    {
        super(controller, portNumber, direction);
    }



    //
    // METHODS
    //




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
}
