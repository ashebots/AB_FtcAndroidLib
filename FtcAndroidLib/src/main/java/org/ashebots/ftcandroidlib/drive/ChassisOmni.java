//This is going to be renamed and refactored later

package org.ashebots.ftcandroidlib.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class ChassisOmni extends BaseChassis {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorFront;
    DcMotor motorBack;

    public ChassisOmni(DcMotor motorLeft, DcMotor motorRight, DcMotor motorFront, DcMotor motorBack)
    {
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.motorFront = motorFront;
        this.motorBack = motorBack;
    }


    //NOTE: currently only supports 4 wheels in cardinal drive formation, where the wheels are placed in north/east/south/west
    //axisX drives left or right (-x = left, +x = right)
    //axisY drives backward or forward (-y = backward, +y = forward)
    //rotation is added after other drive calculations, and controls rotation (-rotation = rotate left, +rotation = rotate right)
    public double Drive(float axisX, float axisY, float rotation)
    {
        axisX = Range.clip(axisX, -1.0f, 1.0f);
        axisY = Range.clip(axisY, -1.0f, 1.0f);
        rotation = Range.clip(rotation, -1.0f, 1.0f);

        double maxDrivePower = 1.0f - Math.abs(rotation);

        double leftPower = Range.clip(axisY, -maxDrivePower, maxDrivePower) + rotation;
        double rightPower = Range.clip(axisY, -maxDrivePower, maxDrivePower) + rotation;
        double frontPower = Range.clip(axisX, -maxDrivePower, maxDrivePower) + rotation;
        double backPower = Range.clip(axisX, -maxDrivePower, maxDrivePower) + rotation;

        this.motorLeft.setPower(leftPower);
        this.motorRight.setPower(rightPower);
        this.motorFront.setPower(frontPower);
        this.motorBack.setPower(backPower);

        return leftPower; //Debugging. Set back to void
    }
}
