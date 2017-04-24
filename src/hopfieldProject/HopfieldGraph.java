package hopfieldProject;

import hopfieldProject.HopfieldNode;

public class HopfieldGraph {
    int[][][][] edges;
    int[][] weights;

    public void addEdge(int k, int l, int i, int j, int weight) {
        edges[k][l][i][j] = weight;
    }
}
