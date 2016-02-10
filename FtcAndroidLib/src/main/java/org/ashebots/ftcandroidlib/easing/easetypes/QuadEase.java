package org.ashebots.ftcandroidlib.easing.easetypes;

import org.ashebots.ftcandroidlib.easing.EaseType;

public class QuadEase implements EaseType {

    @Override
    public double easeIn(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput;
        return endOutput*currentInput*currentInput + startOutput;
    }

    @Override
    public double easeOut(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput;
        return -endOutput * currentInput*(currentInput-2) + startOutput;
    }

    @Override
    public double easeInOut(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput/2;
        if (currentInput < 1) return endOutput/2*currentInput*currentInput + startOutput;
        currentInput--;
        return -endOutput/2 * (currentInput*(currentInput-2) - 1) + startOutput;
    }
}
