package org.ashebots.ftcandroidlib.complexOps;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This class is used to provide a base for all hardware, sensor, and computer functions - for example motors or timers.
public abstract class HardwareComponent {

    private Telemetry telemetry = null;
    //Must give telemetry object before it can be used
    public void SetTelemetry(Telemetry t){
        telemetry = t;
    }
    //Add data to return with telemetry
    // if there is no telemetry object, do nothing and return null
    protected Telemetry.Item AddTelemertyData (String caption, Object value){
        if(telemetry != null){
            return(telemetry.addData(caption, value));
        }
        return (null);
    }
    //Calibrate stores the current encoder position to use in relative encoder tracking.
    public void calibrate() {
    }
    //GetValues calculates new positions of all sensor parts and adjusts relative encoder values.
    public void getValues() {

    }
    //The Stop method stops any movement that may be going on - as in a motor or servo.
    public void stop() {
    }
}
