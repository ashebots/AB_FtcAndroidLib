package org.ashebots.ftcandroidlib.complexOps;

//import required things

//AutoRoutine is a special HardwareComponent used to create autonomous programs easily.
//It automatically runs provided steps and moves on from one when TRUE is inputted into the StateMachine.
//In between steps, it will run the Between function. You can run it like a normal component, using .run();
public abstract class AutoRoutine extends HardwareComponent {
    public double INF = Double.MAX_VALUE;
    Timer timer = new Timer();
    public StateMachine state = new StateMachine();
    //unused status variable - can be called for telemetry
    String s;

    public void run() {
        if (getStep() != -1) {
            //action to execute after step switches
            if (state.ifMoved()) {
                between();
            }
            //executes the action specified by the step number, as well as checking if it should move to the next step
            if (states(getStep())) {
                stop();
                state.state(true, -1);
            }
        }
    }


    //shuts motors off
    public abstract void stop();
    //this is what you put your code into
    public abstract boolean states(int step);
    public void getValues() {}
    public abstract void between();
    public  void calibrate() {}
    public int getStep() {
        return state.getStep();
    }
    public void reset() {
        state = new StateMachine();
    }

    public boolean buttonPressed(HardwareComponent h, String s) { //this would be used to test when a sensor first detected something, not a button.
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
}