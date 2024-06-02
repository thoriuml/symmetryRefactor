package org.thomas.utils;

public class Utils {
    //  ----------------------------------------------------------------------
    //  Maths (ish)
    public static int round(double x) {
        if (x >= 0) {
            return (int) (x + 0.5);
        } else {
            return (int) (x - 0.5);
        }
    }

    public static int constrain(int val, int low, int high) { // If exceeds low or high range return low/high else return val
        if (val < low) {
            return low;
        } else if (val < high) {
            return val;
        } else {
            return high;
        }
    }

    public static int constrain(int val, int high) { // Overload to avoid having to pass in a 0 everytime menu service uses the method
        return constrain(val, 0, high);
    }


    //  ----------------------------------------------------------------------
    //  Hardware interaction

    // Reads voltage off a microcontroller pin.
    // Again, stub function.
    public static int analogRead(int pinNum) {
        return -1;
    }
}
