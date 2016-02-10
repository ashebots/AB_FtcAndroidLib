package org.ashebots.ftcandroidlib.easing.easetypes;


import org.ashebots.ftcandroidlib.easing.EaseType;

public class CircEase implements EaseType {

    @Override
    public double easeIn(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput;
        return -endOutput * (Math.sqrt(1 - currentInput*currentInput) - 1) + startOutput;
    }

    @Override
    public double easeOut(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput;
        currentInput--;
        return endOutput * Math.sqrt(1 - currentInput*currentInput) + startOutput;
    }

    @Override
    public double easeInOut(double currentInput, double startOutput, double endOutput, double endInput) {
        currentInput /= endInput/2;
        if (currentInput < 1) return -endOutput/2 * (Math.sqrt(1 - currentInput*currentInput) - 1) + startOutput;
        currentInput -= 2;
        return endOutput/2 * (Math.sqrt(1 - currentInput*currentInput) + 1) + startOutput;
    }
}
