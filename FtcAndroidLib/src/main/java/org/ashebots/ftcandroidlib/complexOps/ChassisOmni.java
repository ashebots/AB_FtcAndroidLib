//This is going to be renamed and refactored later

package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ChassisOmni extends Chassis {
    DcMotor motorFront;
    DcMotor motorBack;
    double encoderFront;
    double encoderBack;
    double encFOld;
    double encBOld;
    double foff;
    double boff;

    public ChassisOmni(DcMotor motorLeft, DcMotor motorRight, DcMotor motorFront, DcMotor motorBack)
    {
        super(motorLeft,motorRight);
        this.motorFront = motorFront;
        this.motorBack = motorBack;
        motorFront.setDirection(DcMotor.Direction.REVERSE);
    }

    public void omniDrive(double xDist, double yDist) {
        motorFront.setPower(xDist);
        motorBack.setPower(xDist);
        setMotors(yDist);
    }

    public void calibrate() { //sets the current encoder values as 'old' such that getValues can see the difference
        encFOld = motorFront.getCurrentPosition();
        encBOld = motorBack.getCurrentPosition();
        super.calibrate();
    }

    public void getValues() { //adds the difference in encoders that happened last frame
        encoderFront += motorFront.getCurrentPosition() - encFOld;
        encoderBack += motorBack.getCurrentPosition() - encBOld;
        super.getValues();
    }
    //resets encoders
    public void resetEncs() {
        encoderFront = 0;
        encoderBack = 0;
        foff = encFOld = motorFront.getCurrentPosition();
        boff = encBOld = motorBack.getCurrentPosition();
        super.resetEncs();
    }

    public boolean fRange(double min, double max) {
        return (Math.abs(encoderFront) < max && Math.abs(encoderFront) > min);
    }
    public boolean bRange(double min, double max) {
        return (Math.abs(encoderBack) < max && Math.abs(encoderBack) > min);
    }
    public boolean sRange(double min, double max) {
        return ((Math.abs(encoderFront)+Math.abs(encoderBack))/2 < max && (Math.abs(encoderFront)+Math.abs(encoderBack))/2 > min);
    }
    public boolean SRange(double min, double max) {
        double enc = (motorFront.getCurrentPosition()-+motorBack.getCurrentPosition()+foff+boff)/2;
        return enc < max && enc > min;
    }
}
