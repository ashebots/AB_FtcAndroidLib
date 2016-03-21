package org.ashebots.ftcandroidlib.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class ChassisArcade extends BaseChassis {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public ChassisArcade(DcMotor leftMotor, DcMotor rightMotor)
    {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public void Drive(float axisX, float axisY)
    {
        axisX = Range.clip(axisX, -1.0f, 1.0f);
        axisY = Range.clip(axisY, -1.0f, 1.0f);

        double leftPower = Range.clip(axisY + axisX, -1.0f, 1.0f);
        double rightPower = Range.clip(axisY - axisX, -1.0f, 1.0f);

        this.leftMotor.setPower(leftPower);
        this.rightMotor.setPower(rightPower);
    }

    public void DriveForDistance(float distance, float power)
    {
    }
}
