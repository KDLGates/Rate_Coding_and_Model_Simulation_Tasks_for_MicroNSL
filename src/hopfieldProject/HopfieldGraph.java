package hopfieldProject;

import hopfieldProject.HopfieldNode;

public class HopfieldGraph {
    int[][][][] edges = new int[50][50][50][50];
    int[][] weights;

    public void addEdge(int k, int l, int i, int j, int weight) {
        edges[k][l][i][j] = weight;
    }
}
