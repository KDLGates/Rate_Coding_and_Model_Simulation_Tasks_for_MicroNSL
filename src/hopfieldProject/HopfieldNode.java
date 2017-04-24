package hopfieldProject;

public class HopfieldNode extends Leaky {
    int i;
    int j;
    int state;
    float activity = 0f;

    public HopfieldNode() {
        this.i = 0;
        this.j = 0;
        this.state = 0;
        this.activity = 0f;
    }

    public HopfieldNode(int i, int j, int state) {
        this.i = i;
        this.j = j;
        this.state = state;
    }

    public void printDetails() {
        System.out.println("The Hopfield node at coordinates (" + i + ", " + j + ") has State: " + state + ", Activity: " + activity);
    }
}
