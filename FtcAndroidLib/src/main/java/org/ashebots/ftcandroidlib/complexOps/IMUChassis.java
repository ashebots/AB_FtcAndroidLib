package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.hardware.adafruit.*;
import org.ashebots.ftcandroidlib.complexOps.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Art Schell on 3/17/2016.
 */
public class IMUChassis extends Chassis {

    //defines hardware
    public AdafruitBNO055IMU imu;
    long systemTime;
    //sets settings for hardware
    public IMUChassis(DcMotor l, DcMotor r, AdafruitBNO055IMU b){
        super(l,r);
        imu = b;
        systemTime = System.nanoTime();
        imu.initialize();
    }

    //SENSORS - control encoder's or sensor's relative (and absolute) positions.

    double aStand;
    public void setStandard(double angle) {
        aStand = angle;
    }

    //values
    public double angle() {
        return 0;
    }
    public double pitch() {
        return 0;
    }

    //BOOLEANS - return if a sensor value is in a range

    public boolean ARange(double min, double max) {
        return (r(0-aStand) < r(max) && r(0-aStand) > r(min));
    }
    public boolean PRange(double min, double max) {
        return (0 < max && 0 > min);
    }

    //function used to convert a number into a valid angle.
    public double r(double i) {
        return ((i+180)%360)-180;
    }
}
