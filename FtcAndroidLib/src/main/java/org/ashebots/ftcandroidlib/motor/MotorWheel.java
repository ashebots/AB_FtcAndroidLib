package org.ashebots.ftcandroidlib.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

import org.ashebots.ftcandroidlib.motor.Motor;

public class MotorWheel extends Motor
{

    protected float distancePerRevolution; //Typically the wheel's circumference

    public MotorWheel(DcMotor existingDcMotor)
    {
        super(existingDcMotor);
    }


    //
    // METHODS
    //

    public int calculateEncoderTicksForDistance(float distance)
    {
        float rotationsToTarget = distance / this.getDistancePerRevolution();
        float encoderTicksToTarget = rotationsToTarget * this.getEncoderTicksPerRevolution();
        encoderTicksToTarget *= this.getGearRatio();
        return (int) encoderTicksToTarget;
    }

    public void runForDistance(float distance, float power)
    {
        power = Range.clip(power, -1.0f, 1.0f);

        int encoderTicksToTarget = this.getCurrentPosition() + this.calculateEncoderTicksForDistance(distance);

        //this.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.setTargetPosition(encoderTicksToTarget);
        this.setPower(power);
    }


    //
    // GETTERS / SETTERS
    //

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
