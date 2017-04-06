import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.stream.IntStream;
import org.math.plot.*;
import org.apache.commons.lang3.ArrayUtils;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {


        long T = 0;
        // As A is the rate of decay, A will need to be improvised for now.
        // TODO: What is a good value for A? Why?
        // float A = Float.valueOf("5E-5");
        float A = 0.25f;
        System.out.println("Rate of Decay is: " + A);
        // TODO: What is a good value for K? Why?
        // float K = ThreadLocalRandom.current().nextFloat();
        float K = 2.0F;

        Leaky leakyIntegrator = new Leaky(A,K,T);
        ArrayList<Float> potentialsByTime = new ArrayList<Float>();


        // for 1 - 5 nextV (constant C)
        // TODO: What is a good value for C? Why?
        float C = 2.0f;
        float baseC = C;
        System.out.println("Initial Constant Input is: " + C);
        // System.out.println("At time 0, the initial voltage is: " + leakyIntegrator.NextV( 0f, T));


        for (; T <= 50; ++T) {
            potentialsByTime.add(leakyIntegrator.NextV(C, T));
            System.out.println("Voltage at time " +  T + ": " + potentialsByTime.get((int) T));
        }

        // For 6-20 nextV (c=0)
        C = 0;
        System.out.println("At this point, input has ceased (C=0).");
        for (; T <= 100; ++T) {
            potentialsByTime.add(leakyIntegrator.NextV(C, T));
            System.out.println("Voltage at time " + T +": " + potentialsByTime.get((int) T));
        }

        /*

        T = 0;
        Leaky leakyIntegrator2 = new Leaky(A,potentialsByTime.get(11).longValue(),T);
        ArrayList<Float> potentialsByTime2 = new ArrayList<Float>();
        // for 1 - 5 nextV (constant C)
        // TODO: What is a good value for C? Why?
        C = 1.0f;
        System.out.println("Initial Constant Input is: " + C);
        System.out.println("At time 0, the initial voltage is: " + leakyIntegrator2.NextV( 0f, T));
        for (; T <= 5; ++T) {
            potentialsByTime2.add(leakyIntegrator2.NextV(C, T));
            System.out.println("Voltage at time " +  T + ": " + potentialsByTime2.get((int) T));
        }

        // For 6-20 nextV (c=0)
        C = 0;
        System.out.println("At this point, input has ceased (C=0).");
        for (; T <= 20; ++T) {
            potentialsByTime2.add(leakyIntegrator2.NextV(C, T));
            System.out.println("Voltage at time " + T +": " + potentialsByTime2.get((int) T));
        }

        T = 0;
        Leaky leakyIntegrator3 = new Leaky(A,potentialsByTime2.get(11).longValue(),T);
        ArrayList<Float> potentialsByTime3 = new ArrayList<Float>();
        // for 1 - 5 nextV (constant C)
        // TODO: What is a good value for C? Why?
        C = 1.0f;
        System.out.println("Initial Constant Input is: " + C);
        System.out.println("At time 0, the initial voltage is: " + leakyIntegrator3.NextV( 0f, T));
        for (; T <= 5; ++T) {
            potentialsByTime3.add(leakyIntegrator3.NextV(C, T));
            System.out.println("Voltage at time " +  T + ": " + potentialsByTime3.get((int) T));
        }

        // For 6-20 nextV (c=0)
        C = 0;
        System.out.println("At this point, input has ceased (C=0).");
        for (; T <= 20; ++T) {
            potentialsByTime3.add(leakyIntegrator3.NextV(C, T));
            System.out.println("Voltage at time " + T +": " + potentialsByTime3.get((int) T));
        }

        */

        // Convert to doubles array for jmathplot
        double[] potentialDoubles = new double[potentialsByTime.size()];
        int i = 0;

        for (Float f : potentialsByTime) {
            potentialDoubles[i++] = (f != null ? f : Float.NaN);
        }

        // Simple doubles array for Y Axis
        double[] timeIntegers = new double[21];
        for (i = 0; i < timeIntegers.length; ++i) {
            timeIntegers[i] = i + 1;
        }

        // Plot results by time using jmathplot
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 1 / 2;
        int width = screenSize.width * 1 / 2;

        Plot2DPanel plot = new Plot2DPanel();
        plot.addLinePlot("Membrane Potential by Time", timeIntegers, potentialDoubles);
        JFrame frame = new JFrame("Membrane Potential by Time");
        frame.setSize(width, height);
        frame.setContentPane(plot);
        frame.setVisible(true);


        /*
        long baseTime = ThreadLocalRandom.current().nextLong();
        long timeToLeak = Long.MIN_VALUE;ime
        while baseTime >= prospectiveLeakTime {
            timeToLeak = ThreadLocalRandom.current().nextLong();
        }
        leakTime = first_time - base_time;
        */

    }
}
