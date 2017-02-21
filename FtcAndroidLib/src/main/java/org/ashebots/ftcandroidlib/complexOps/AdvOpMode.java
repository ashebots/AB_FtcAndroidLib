package org.ashebots.ftcandroidlib.complexOps;

import android.util.Log;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

//AdvOpMode adds a few new functions to the original OpMode.
public abstract class AdvOpMode extends OpMode {
    protected final int encoderConstant = 615;
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

    //faster ways to generate teleop chassis (drive, omni, mechanum)
    public Chassis chassis(String lName, String rName) {
        return new Chassis(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName), null);
    }
    public Chassis imuchassis(String lName, String rName, String iName) {
        BNO055IMU bno = hardwareMap.get(BNO055IMU.class,iName);
        return new Chassis(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),bno);
    }

    public ChassisOmni chassisomni(String lName, String rName, String fName, String bName) {
        return new ChassisOmni(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),hardwareMap.dcMotor.get(fName),hardwareMap.dcMotor.get(bName),null);
    }
    public ChassisOmni imuchassisomni(String lName, String rName, String fName, String bName, String iName) {
        BNO055IMU bno = hardwareMap.get(BNO055IMU.class,iName);
        return new ChassisOmni(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),hardwareMap.dcMotor.get(fName),hardwareMap.dcMotor.get(bName),bno);
    }
    
    public ChassisMechanum chassismechanum(String lName, String rName, String lbName, String rbName) {
        return new ChassisMechanum(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),hardwareMap.dcMotor.get(lbName),hardwareMap.dcMotor.get(rbName),null);
    }
    public ChassisMechanum imuchassismechanum(String lName, String rName, String lbName, String rbName, String iName) {
        BNO055IMU bno = hardwareMap.get(BNO055IMU.class,iName);
        return new ChassisMechanum(hardwareMap.dcMotor.get(lName),hardwareMap.dcMotor.get(rName),hardwareMap.dcMotor.get(lbName),hardwareMap.dcMotor.get(rbName),bno);
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
