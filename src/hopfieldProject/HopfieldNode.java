package hopfieldProject;

public class HopfieldNode extends Leaky {
    int i;
    int j;
    int k;
    int l;
    int state;
    float activity = 0f;

    public HopfieldNode() {
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.state = 0;
        this.activity = 0f;
    }

    public HopfieldNode(int i, int j, int state) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        this.state = state;
    }

    public void printDetails() {
        System.out.println("The Hopfield node at coordinates (" + i + ", " + j + ") has State: " + state + ", Activity: " + activity);
    }
}
