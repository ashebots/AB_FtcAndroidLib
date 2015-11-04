package org.ashebots.ftcandroidlib.easing;


//Each type of easing must implement this interface.
//The parameter order and ease types come from the original eases developed by Robert Penner
//TODO: Document this more

public interface EaseType {

    double easeIn(double currentInput, double startOutput, double endOutput, double endInput);

    double easeOut(double currentInput, double startOutput, double endOutput, double endInput);

    double easeInOut(double currentInput, double startOutput, double endOutput, double endInput);
}
