//This is going to be renamed and refactored later

package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ChassisMechanum extends Chassis {
    DcMotor motorLeftB;
    DcMotor motorRightB;
    double encoderMechanum;

    public ChassisMechanum(DcMotor motorLeft, DcMotor motorRight, DcMotor motorLeftB, DcMotor motorRightB, BNO055IMU i)
    {
        super(motorLeft,motorRight, i);
        this.motorLeftB = motorLeftB;
        this.motorRightB = motorRightB;
        motorRightB.setDirection(DcMotor.Direction.REVERSE);
    }

    //turns back wheels with front wheels in normal drive mode
    public void setMotors(double x) {
        motorLeftB.setPower(x);
        motorRightB.setPower(x);
        super.setMotors(x);
    }
    public void turnMotors(double x) {
        motorLeftB.setPower(-x);
        motorRightB.setPower(x);
        super.turnMotors(x);
    }
    public void moveMotors(double l, double r) {
        motorLeftB.setPower(l);
        motorRightB.setPower(r);
        super.moveMotors(l,r);
    }

    //This mode is Mechanum Mode. This will move the robot in a straight line in any direction, without turning.
    //This works by turning the wheels on each side a different amount. Since the mechanum rollers on each wheel
    //are no longer canceled out by each other's movement, they will move the robot diagonally.
    public void mechanumWheels(double xDist, double yDist) {
        double leftFPair = 0; //left front, right back
        double rightFPair =0; //right front, left back
        //Y distance moves both pairs of motors forward equally.
        leftFPair = rightFPair = (Math.sqrt(2)/2)*yDist;
        //X distance moves the right pair forward and the left pair backward, moving the robot right.
        leftFPair -= (Math.sqrt(2)/2)*xDist;
        rightFPair -= (Math.sqrt(2)/2)*xDist;
        motorLeft.setPower(leftFPair);
        motorRightB.setPower(leftFPair);
        motorRight.setPower(rightFPair);
        motorLeftB.setPower(rightFPair);
    }

    public double getEncMechanum() {
        return Math.sqrt(encoderLeft*encoderLeft+encoderRight*encoderRight); //returns the distance formula for both, since they are at right angles
    }
}
