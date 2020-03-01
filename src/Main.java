import customgraph.Graph;
import vRandom.MinDsat;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(2000);
        g.addEdge(0,1);
        g.addEdge(1,4);
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(2,5);
        g.addEdge(3,4);

        new MinDsat(g);
    }
}
