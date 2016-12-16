package org.ashebots.ftcandroidlib.complexOps;

//Returns if a joystick is being used or not, also the motor values associated with the joystick x and y.
public class JoyEvent {
    boolean old = false;
    double fwdPower;
    double trnPower;
    double maxTurnW;
    double limit;
    public int joymode = 0;
    public JoyEvent(double FSpd,double TSpd,double mtw) {
        fwdPower = FSpd;
        trnPower = TSpd;
        maxTurnW = mtw;
    }

    public String parse(double x, double y) {
        boolean b = (x!=0.0 || y!=0.0);
        String a = "UNPRESSED"; //If the joystick is at 0,0
        if (b&&old) a = "HELD"; //If the joystick is being used
        if (!b&&old) a = "RELEASED"; //If the joystick just stopped being used
        if (b&&!old) a = "PRESSED"; //If the joystick is starting to be used
        old = (x!=0.0 || y!=0.0);
        return a;

    }
    public double[] calc(double x, double y) {
        //change turning rad if neccesary
        x *= maxTurnW;
        //Calculate the ChassisArcade
        double l = -y - x;
        double r = -y + x;
        //Calculate distance from center
        double distance = Math.sqrt(x*x+y*y);
        if (Math.abs(distance)>1.0) {
            distance = 1.0;
        }
        //Does trigonometry to figure out what percentage of the joystick is straight and what is turn.
        double angle = Math.asin(y/distance);
        if (distance == 0) {
            angle = 0.0;
        }
        double angleOrg = angle;
        angle /= Math.PI/2;
        angle = Math.abs(angle);
        //Figures out speed coeficcient
        double spd = trnPower + angle * (fwdPower - trnPower);

        double[] movement = {l*spd/Math.sqrt(2),r*spd/Math.sqrt(2)}; //the Square Root 2 divide is neccesary to trim 45
        //degree turns on a 1-1-1 joystick. This is because with Arcade these turns end up being Sqrt 2 power to 1 motor, 0 power to another.
        return movement;
    }
}