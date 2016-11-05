package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.hardware.adafruit.*;
import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.navigation.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Art Schell on 3/17/2016.
 */
public class IMUChassis extends Chassis {
    //defines hardware
    public BNO055IMU imu;
    Orientation angles;

    //sets settings for hardware
    public IMUChassis(DcMotor l, DcMotor r, BNO055IMU b){
        super(l,r);
        imu = b;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(parameters);

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    //Calculation of angle units:
    private void calc() {
        angles   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
    }

    double aStand;
    public void setStandard(double angle) {
        aStand = angle;
    }

    //values
    public double angle() {
        calc();
        return -AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle); //heading is negative because the +/- dirs were reversed
    }
    public double roll() {
        calc();
        return AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.secondAngle);
    }
    public double pitch() {
        calc();
        return AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.thirdAngle);
    }

    //BOOLEANS - return if a sensor value is in a range

    public boolean ARange(double min, double max) {
        return (r(angle()-aStand) < r(max) && r(angle()-aStand) > r(min));
    }
    public boolean PRange(double min, double max) {
        return (pitch() < max && pitch() > min);
    }
    public boolean RRange(double min, double max) {
        return (roll() < max && roll() > min);
    }

    //function used to convert a number into a valid angle.
    public double r(double i) {
        return ((i+180)%360)-180;
    }
}
