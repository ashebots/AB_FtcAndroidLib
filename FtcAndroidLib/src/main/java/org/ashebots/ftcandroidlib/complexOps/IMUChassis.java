package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Art Schell on 3/17/2016.
 */
public class IMUChassis extends Chassis {
    //defines hardware
    public GyroSensor imu;
    long systemTime;
    //sets settings for hardware
    public IMUChassis(DcMotor l, DcMotor r, GyroSensor b){
        super(l,r);
        imu = b;
        systemTime = System.nanoTime();
        imu.calibrate();
    }

    //SENSORS - control encoder's or sensor's relative (and absolute) positions.

    double aStand;
    public void setStandard(double angle) {
        aStand = angle;
    }

    //values
    public double angle() {
        return imu.getHeading();
    }
    public double pitch() {
        return imu.rawX();
    }

    //BOOLEANS - return if a sensor value is in a range

    public boolean ARange(double min, double max) {
        return (r(imu.getHeading()-aStand) < r(max) && r(imu.getHeading()-aStand) > r(min));
    }
    public boolean PRange(double min, double max) {
        return (imu.rawX() < max && imu.rawX() > min);
    }

    //function used to convert a number into a valid angle.
    public double r(double i) {
        return ((i+180)%360)-180;
    }
}
