package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class Chassis extends HardwareComponent {
    //defines hardware
    public DcMotor motorRight;
    public DcMotor motorLeft;
    //defines variables
    public double encLOld = 0;
    public double encROld = 0;
    public double encoderLeft = 0;
    public double encoderRight = 0;
    public double loff = 0;
    public double roff = 0;
    //sets settings for hardware
    public Chassis(DcMotor l, DcMotor r) {
        motorLeft = l;
        motorRight = r;
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    //SENSORS - control encoder's or sensor's relative (and absolute) positions.

    @Override
    public void calibrate() { //sets the current encoder values as 'old' such that getValues can see the difference
        encLOld = motorLeft.getCurrentPosition();
        encROld = motorRight.getCurrentPosition();
    }
    @Override
    public void getValues() {
        encoderLeft += motorLeft.getCurrentPosition() - encLOld;
        encoderRight += motorRight.getCurrentPosition() - encROld;
    }
    //resets encoders
    public void resetEncs() {
        encoderLeft = 0;
        encoderRight = 0;
        loff = encLOld = motorLeft.getCurrentPosition();
        roff = encROld = motorRight.getCurrentPosition();
    }

    double aStand;
    public void setStandard(double angle) {
        aStand = angle;
    }

    //BOOLEANS - return if a sensor value is in a range

    public boolean lRange(double min, double max) {
        return (Math.abs(encoderLeft) < max && Math.abs(encoderLeft) > min);
    }
    public boolean rRange(double min, double max) {
        return (Math.abs(encoderRight) < max && Math.abs(encoderRight) > min);
    }
    public boolean mRange(double min, double max) {
        return ((Math.abs(encoderLeft)+Math.abs(encoderRight))/2 < max && (Math.abs(encoderLeft)+Math.abs(encoderRight))/2 > min);
    }
    public boolean aRange(double min, double max) {
        double enc = (motorLeft.getCurrentPosition()-+motorRight.getCurrentPosition()+loff+roff)/2;
        return enc < max && enc > min;
    }

    //FUNCTIONS - move the object

    //moves forward or back
    public String setMotors(double x) {
        motorLeft.setPower(x);
        motorRight.setPower(x);
        return "Moving forward";
    }

    //turns
    public String turnMotors(double x) {
        motorLeft.setPower(x);
        motorRight.setPower(-x);
        return "Turning";
    }
    public String moveMotors(double l, double r) {
        motorLeft.setPower(l);
        motorRight.setPower(r);
        return "Moving";
    }
    @Override
    public void stop() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
        getValues();
    }
}