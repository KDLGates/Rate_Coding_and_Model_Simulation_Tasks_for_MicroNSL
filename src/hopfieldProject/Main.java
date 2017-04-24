package hopfieldProject;

import hopfieldProject.HopfieldNode;
import hopfieldProject.HopfieldGraph;
import hopfieldProject.Leaky;

import java.lang.Math;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;

import org.math.plot.*;

public class Main {

    public static void main(String[] args) {
        // A 10x5 array where 1 represents a filled cell, and 0 represents an empty one.
        // This array represents a 'correct' letter A.
        int[][] A_Array = new int[][] {
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 1, 1, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }
        };

        // A 10x5 array where 1 represents a filled cell, and 0 represents an empty one.
        // This array represents a 'corrupted' letter A (it's missing the horizontal line).
        int[][] Corrupted_A_Array = new int[][] {
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }
        };

        // This network is dual coordinates: think of i,j as the 2D coordinates of the
        // nodes, and j,k as the corresponding 2D coordinates of the cells in the input arrays.

        // Initialise the Hopfield Graph that'll track the nodes and edges.
        // The edges will be a 2D coordinate grid of k and l that'll correspond to the coordinates of the input array.
        HopfieldGraph graph = new HopfieldGraph();

        // Initialise the Hopfield Nodes that'll correspond to our evaluation of the input array.
        // They're a 2D coordinate grid of i and j, but they'll be double indexed afterward.
        HopfieldNode[][] nodes = new HopfieldNode[5][10];
        for (int i = 0; i < nodes.length; ++i) {
            for (int j = 0; j < nodes[i].length; ++j) {
                // We translate the input array into the initial neuron state (mf_(kl) in the book).
                // The state corresponds to whether that neuron's cell is filled (1) or empty (0).
                // This is the initial training for the nodes to memorize the 'correct' letter A.
                nodes[i][j] = new HopfieldNode(i,j, A_Array[i][j]);
            }
        }

        // Populate the network with edges (across the i,j coordinates) until it is fully connected.
        for (int i = 0; i < nodes.length; ++i) {
            for (int j = 0; j < nodes[i].length; ++j) {
                if (i == nodes[i][j].i && j == nodes[i][j].j) {
                    continue;
                }
                else {
                    graph.addEdge(nodes[i][j].k, nodes[i][j].l, i, j, nodes[i][j].state);
                }
            }
        }


        // T is our time coordinate, and it will be re-used across both the Hopfield and Leaky demos.
        long T = 0;

        // C is our initial constant voltage to start off the leaky integrators.
        // TODO: What is a good initial value for C? Why?
        float C = 1.0f;
        float baseC = C;

        // For 75 time units, we calculate the next voltages for all 50 of the leaky Hopfield nodes.
        // This will be used for the threshhold.
        for (; T <= 75; ++T) {
            for (int i = 0; i < nodes.length; ++i) {
                for (int j = 0; j < nodes[i].length; ++j) {
                    nodes[i][j].NextV(C, T);
                }
            }
        }

        // Reset time to 0 for the Leaky demo.
        T = 0;

        // A is the rate of exponential decay (the 'leak' of the leaky integrator).
        // TODO: What is a good value for A? Why?
        // float A = Float.valueOf("5E-5");
        float A = 0.25f;
        System.out.println("Rate of Decay is: " + A);

        // K is some allowable constant input term.
        // TODO: What is a good value for K? Why?
        // float K = ThreadLocalRandom.current().nextFloat();
        float K = 2.0F;

        Leaky leakyIntegrator = new Leaky(A,K,T);
        ArrayList<Float> potentialsByTime = new ArrayList<Float>();


        // for 1 - 5 nextV (constant C)
        C = 1.0f;
        System.out.println("Initial Constant Input is: " + C);
        // System.out.println("At time 0, the initial voltage is: " + leakyIntegrator.NextV( 0f, T));


        for (; T <= 75; ++T) {
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
        hopfieldProject.Leaky leakyIntegrator2 = new hopfieldProject.Leaky(A,potentialsByTime.get(11).longValue(),T);
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
        hopfieldProject.Leaky leakyIntegrator3 = new hopfieldProject.Leaky(A,potentialsByTime2.get(11).longValue(),T);
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
