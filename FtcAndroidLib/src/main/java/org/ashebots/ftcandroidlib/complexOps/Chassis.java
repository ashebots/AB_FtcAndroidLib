package org.ashebots.ftcandroidlib.complexOps;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Chassis extends HardwareComponent {
    //MOTORS
    //defines hardware
    public DcMotor motorRight;
    public DcMotor motorLeft;
    public BNO055IMU imu;
    Orientation angles;
    //defines variables
    public double encLOld = 0;
    public double encROld = 0;
    public double encoderLeft = 0;
    public double encoderRight = 0;
    public double loff = 0;
    public double roff = 0;

    boolean Drive4 = false;
    DcMotor motorRightBack;
    DcMotor motorLeftBack;

    //sets settings for hardware
    public Chassis(DcMotor l, DcMotor r, BNO055IMU i) {
        motorLeft = l;
        motorRight = r;
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setMaxSpeed(6000);
        motorRight.setMaxSpeed(6000);

        imu = i;

        if (imu != null) {
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            //parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            imu.initialize(parameters);

            imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        }
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

    //Calculation of angle units:
    private void calc() {
        angles = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
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
        double enc = (motorLeft.getCurrentPosition()+motorRight.getCurrentPosition()-loff-roff)/2;
        return enc < max && enc > min;
    }
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

    //FUNCTIONS - move the object

    //moves forward or back
    public void setMotors(double x) {
        motorLeft.setPower(x);
        motorRight.setPower(x);
        if (Drive4) {
            motorLeftBack.setPower(x);
            motorRightBack.setPower(x);
        }
    }

    //turns
    public void turnMotors(double x) {
        motorLeft.setPower(-x);
        motorRight.setPower(x);
        if (Drive4) {
            motorLeftBack.setPower(-x);
            motorRightBack.setPower(x);
        }
    }

    public double targetAngle = -200; //unused
    //moves each motor individually
    public void moveMotors(double l, double r) {
        motorLeft.setPower(l);
        motorRight.setPower(r);
        if (Drive4) {
            motorLeftBack.setPower(l);
            motorRightBack.setPower(r);
        }
    }
    public void omniDrive(double xDist, double yDist) {
        double angle = Math.asin(xDist)/Math.PI*180;
        double aDiff = angle() - angle;
        if (Math.abs(aDiff) < 2.5) {
            getValues();
            setMotors(-1);
            calibrate();
        } else {
            if (aDiff<0) { //which angle to turn
                turnMotors(0.2);
            } else {
                turnMotors(-0.2);
            }
        }
    }

    @Override
    public void stop() {
        setMotors(0);
    }

    public double getEncMechanum() { //just returns the normal encoder value
        return (encoderLeft + encoderRight) / 2;
    }

    public void Add4WheelDrive(Chassis back) {
        Drive4 = true;
        motorLeftBack = back.motorLeft;
        motorRightBack = back.motorRight;
        motorRightBack.setDirection(DcMotor.Direction.REVERSE);
    }
}
