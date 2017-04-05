import java.lang.Math;

public class Leaky {
    float nextVoltage;
    float prevVoltage;
    long time;
    float A;
    float K;

    float NextV(float C, long newT) {
        // nextVoltage = (float) (K * Math.exp(-A * (newT - time)) + C) + nextVoltage;

        if (newT == 0 && time == newT) {
            nextVoltage = 0;
            prevVoltage = 0;
        } else {
            nextVoltage = (float) (K * Math.exp(-A * time) + C) + prevVoltage;
            prevVoltage = (float) (time + C);
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
        // time = newT;
        return prevVoltage;
    }

    public Leaky(float A, float K, long T) {
        this.time = T;
        this.K = K;
        this.A = A;
        this.nextVoltage = 0; //K * Math.exp(-A * t + 1) + prevVoltage;
    }
}
