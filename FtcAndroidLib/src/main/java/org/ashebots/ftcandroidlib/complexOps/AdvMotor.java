package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Art Schell on 3/17/2016.
 */
public class AdvMotor extends HardwareComponent {
    //defines hardware
    DcMotor motor;
    //defines variables
    double encOld = 0;
    double enc = 0;
    double offset = 0;
    //sets settings for hardware
    public AdvMotor(DcMotor m) {
        motor = m;
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void reverse(){
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    //SENSORS - control encoder's or sensor's relative (and absolute) positions.

    @Override
    public void calibrate() { //sets the current encoder value as 'old' such that getValues can see the difference
        encOld = motor.getCurrentPosition();
    }
    @Override
    public void getValues() {
        enc += motor.getCurrentPosition() - encOld;
    }

    //resets encoders
    public void resetEncs() {
        enc = 0;
        offset = encOld = motor.getCurrentPosition();
    }

    public double getEnc() {
        return motor.getCurrentPosition();
    }

    public boolean mRange(double min, double max) { //Relative Range (the encoder value tracked while THIS OBJECT is moving the motor)
        return (Math.abs(enc) < max && Math.abs(enc) > min);
    }
    public boolean aRange(double min, double max) { //Absolute Range (the actual encoder value)
        return (Math.abs((motor.getCurrentPosition()-offset)) < max && Math.abs((motor.getCurrentPosition()-offset)) > min);
    }

    //FUNCTIONS - move the object

    public void setMotor(double x) {
        motor.setPower(x);
    }

    public void stop() {
        motor.setPower(0);
        getValues();
    }
    long lastTime;
    int lastPos;
    public double speed() {
        long time = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        int pos = motor.getCurrentPosition() - lastPos;
        lastPos = motor.getCurrentPosition();
        return pos / time * 1000;
    }
    public void setTargetSpeed(int tps) {
        motor.setMaxSpeed(tps);
    }
}
