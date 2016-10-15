package org.ashebots.ftcandroidlib.complexOps;

import android.util.Log;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

//AdvOpMode adds a few new functions to the original OpMode.
public abstract class AdvOpMode extends OpMode {
    public Logger l = new Logger();
    public double INF = Double.MAX_VALUE;
    //A faster way to generate a motor.
    public AdvMotor mtr(String name) {
        return new AdvMotor(hardwareMap.dcMotor.get(name));
    }
    //A faster way to generate a servo.
    public Servo srv(String name) {
        return hardwareMap.servo.get(name);
    }
    //A faster way to generate an IMU Chassis.
    public IMUChassis imuchassis(String lName, String rName, String bName) {
        AdafruitBNO055IMU bno = new AdafruitBNO055IMU(hardwareMap.i2cDeviceSynch.get(bName));
        return new IMUChassis(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),bno);
    }
    public Chassis chassis(String lName, String rName) {
        return new Chassis(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName));
    }
    public ChassisOmni chassisomni(String lName, String rName, String fName, String bName) {
        return new ChassisOmni(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),hardwareMap.dcMotor.get(fName),hardwareMap.dcMotor.get(bName));
    }
    //This function automatically calibrates relative sensor values based on the state of a button.
    //It returns true when the part should activate.
    public boolean buttonPressed(HardwareComponent h, String s) {
        telemetry.addData("Status",s);
        if (s.equals("PRESSED")) {
            h.calibrate();
            return true;
        } else if (s.equals("HELD")) {
            h.getValues();
            h.calibrate();
            return true;
        } else if (s.equals("RELEASED")) {
            h.stop();
        }
        return false;
    }
    public AdvUltrasonic sonic(String name, String legacy, int port) {
        return new AdvUltrasonic(hardwareMap.ultrasonicSensor.get(name),hardwareMap.legacyModule.get(legacy),port);
    }
}
