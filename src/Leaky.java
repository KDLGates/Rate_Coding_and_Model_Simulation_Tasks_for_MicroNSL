import java.lang.Math;

public class Leaky {
    float nextVoltage;
    float prevVoltage;
    long time;
    float A;
    float K;

    float NextV(float C, long newT) {
        // nextVoltage = (float) (K * Math.exp(-A * (newT - time)) + C) + nextVoltage;

        // The critical block which calculates the result.
        // If we have a "true" zero start time (initial time of initializer and time is zero), zero out voltages...
        if (newT == 0 && time == newT) {
            nextVoltage = 0;
            prevVoltage = 0;
        } else {
            // Else, apply the closed form solution to the leaky integrator equation (per Wikipedia)
            nextVoltage = (float) (K * Math.exp(-A * time) + C) + prevVoltage;

            // Options for prevVoltage (the "+x_0(t)" term on Wikipedia):
            // Option #1: The previous solution
            prevVoltage = (float) (K * Math.exp(-A * time) + C);
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

        System.out.println("Debug: At time " + newT + ", (" + K + " * e^(-" + A + " * (" + newT + " - " + time + ") + " + C + ") is: " + (K * Math.exp(-A * (newT - time)) + C));
        time = newT;
        return prevVoltage;
    }

    public Leaky(float A, float K, long T) {
        this.time = T;
        this.K = K;
        this.A = A;
        this.nextVoltage = 0; //K * Math.exp(-A * t + 1) + prevVoltage;
    }
}
