package org.ashebots.ftcandroidlib.easing.easetypes;

import org.ashebots.ftcandroidlib.easing.EaseType;


//Note: all of these are the same.
public class Linear implements EaseType {

    @Override
    public double easeIn(double currentInput, double startOutput, double endOutput, double endInput) {
        return endOutput*currentInput/endInput + startOutput;
    }

    @Override
    public double easeOut(double currentInput, double startOutput, double endOutput, double endInput) {
        return endOutput*currentInput/endInput + startOutput;
    }

    @Override
    public double easeInOut(double currentInput, double startOutput, double endOutput, double endInput) {
        return endOutput*currentInput/endInput + startOutput;
    }
}
