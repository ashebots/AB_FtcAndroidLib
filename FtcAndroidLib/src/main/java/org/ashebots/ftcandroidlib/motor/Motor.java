package org.ashebots.ftcandroidlib.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

public class Motor extends DcMotor
{
    protected float gearRatio = 1.0f;
    protected float powerMultiplier = 1.0f;

    protected int encoderTicksPerRevolution = 0; //1440 for Tetrix, 1120 for AndyMark NeveRest 40:1, 1680 for AndyMark NeveRest 60:1. I think

    //Number of encoder ticks added to actual encoder count. Used to "reset" encoder.
    protected int currentPositionOffset = 0;


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

    //OVERRIDDEN

    @Override
    public void setPower(double power)
    {
        if (Double.isNaN(power))
        {
            power = 0;
        }

        power = Range.clip(power, -1, 1);
        super.setPower(power);
    }

    @Override
    public int getCurrentPosition()
    {
        return this.getCurrentPositionRaw() + this.getCurrentPositionOffset();
    }

    @Override
    public void setTargetPosition(int targetPosition)
    {
        int newPosition = targetPosition - this.getCurrentPositionOffset();

        super.setTargetPosition(newPosition);
    }

    @Override
    public int getTargetPosition()
    {
        return super.getTargetPosition() + this.getCurrentPositionOffset();
    }


    //NEW

    //currentPosition
    public void setCurrentPosition(int newPosition)
    {
        int newOffset = -this.getCurrentPositionRaw() + newPosition;
        this.setCurrentPositionOffset(newOffset);
    }

    //currentPositionRaw
    public int getCurrentPositionRaw()
    {
        return super.getCurrentPosition();
    }

    //currentPositionOffset
    public int getCurrentPositionOffset()
    {
        return this.currentPositionOffset;
    }

    //This shouldn't be used by the user
    protected void setCurrentPositionOffset(int offset)
    {
        this.currentPositionOffset = offset;
    }

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
