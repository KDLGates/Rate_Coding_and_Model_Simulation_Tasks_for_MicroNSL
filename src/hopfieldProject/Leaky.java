package hopfieldProject;

import java.lang.Math;

public class Leaky {
    float nextVoltage;
    float prevVoltage;
    long time;
    float A;
    float K;

    float NextV(float C, long newT) {
        // nextVoltage = (float) (K * Math.exp(-A * (newT - time)) + C) + nextVoltage;
        // nextVoltage = (float) (K * Math.exp(-A * time)) + prevVoltage;
        // prevVoltage = (float) (K * Math.exp(-A * time));

        // The critical block which calculates the result.
        // If we have a "true" zero start time (initial time of initializer and time is zero), zero out voltages...

        if (newT == 0 && time == newT) {
            nextVoltage = 0;
            prevVoltage = 0;
            // prevVoltage = (float) (K * Math.exp(-A * time));
        } else {
            // Else, apply the closed form solution to the leaky integrator equation (per Wikipedia)
            // nextVoltage = (float) (K * Math.exp(-A * time) + C) + prevVoltage;
            nextVoltage = (float) (K * Math.exp(-A * time)) + prevVoltage;

            // Options for prevVoltage (the "+x_0(t)" term on Wikipedia):
            // Option #1: The previous solution
            // prevVoltage = (float) (K * Math.exp(-A * time) + C);
            prevVoltage = (float) (K * Math.exp(-A * time));
        }


        /*
        if (newT == 0 && time == newT) {
            nextVoltage = 0;
            prevVoltage = 0;
        } else {
            nextVoltage = (float) (K * Math.exp(-A * time) + C) + prevVoltage;
            prevVoltage = (float) ((time - 1) + C);
        }
        */

        // System.out.println("Debug: At time " + newT + ", (" + K + " * e^(-" + A + " * (" + newT + " - " + time + ") + " + C + ") is: " + (K * Math.exp(-A * (newT - time)) + C));
        System.out.println("Debug: At time " + newT + ", (" + K + " * e^(-" + A + " * (" + newT + ") + " + C + ") is: " + (K * Math.exp(-A * (newT)) + C));
        time = newT;
        return nextVoltage;
    }

    public Leaky() {
        this.time = 0;
        this.A = 0.25f;
        this.K = 2.0f;
        this.nextVoltage = 0;
        this.prevVoltage = 0;
    }

    public Leaky(float A, float K, long T) {
        this.time = T;
        this.K = K;
        this.A = A;
        this.nextVoltage = 0;
        this.prevVoltage = 0;
    }
}
