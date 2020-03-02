import Brelaz.DSAT;
import customgraph.Graph;
import vRandom.MinDsat;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(8);/*
        g.addEdge(0,1);
        g.addEdge(1,4);
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(2,5);
        g.addEdge(3,4);*/
        g.addEdge(0,4);
        g.addEdge(0,5);
        g.addEdge(0,6);
        g.addEdge(1,2);
        g.addEdge(1,4);
        g.addEdge(1,7);
        g.addEdge(2,6);
        g.addEdge(3,5);
        g.addEdge(3,6);
        g.addEdge(5,6);
        g.addEdge(5,7);
        g.addEdge(6,7);
        //new MinDsat(g);
        new DSAT(g);
    }
}
