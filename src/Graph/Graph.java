package Graph;

import java.util.Arrays;
import java.util.LinkedList;
//Contributed by Sumit Ghosh, GeeksForGeeks
//Graph implementation : Undirected, unweighted, LinkedList
public class Graph {
    private int V;
    private LinkedList<Integer> adjListArray[];
    private int[] vColors; // Stores the colors of the vertices
    private long start;
    private long finish;
    private int edges;
    private boolean directed;

    public int getEdges() {
        if (directed){
            return edges/2;
        }
        else
            return edges;
    }


    public void startTimer(){
    start = System.nanoTime();
    }
    /**
     *
     * @return time elapsed in milliseconds
     */
    public double getTime(){
    finish = System.nanoTime();
    return (double)(finish-start)/1000000;
    }
    public int[] getvColors() {
        return vColors;
    }

    // constructor
    public Graph(int V)
    {
        this.edges = 0;
        this.directed = false;
        this.V = V;
        // define the size of array as
        // number of vertices
        adjListArray = new LinkedList[V];

        // Create a new list for each vertex
        // such that adjacent nodes can be stored
        for(int i = 0; i < V ; i++){
            adjListArray[i] = new LinkedList<>();
        }
        this.vColors = new int[V];
    }
    public int getV() {
        return V;
    }
    public LinkedList<Integer>[] getAdjListArray() {
        return adjListArray;
    }

    // Adds an edge to an undirected text2Graph
    public void addEdge(Graph g, int src, int dest)
    {
        directed = false;
        // Add an edge from src to dest.
        g.adjListArray[src].add(dest);

        // Since text2Graph is undirected, add an edge from dest
        // to src also
        g.adjListArray[dest].add(src);
        edges++;
    }

    // A utility function to print the adjacency list
    // representation of text2Graph
    public void printGraph(Graph graph)
    {
        for(int v = 0; v < graph.V; v++)
        {
            System.out.println("Adjacency list of vertex "+ v);
            System.out.print("head");
            for(Integer pCrawl: graph.adjListArray[v]){
                System.out.print(" -> "+pCrawl);
            }
            System.out.println("\n");
        }
    }

    //temporary
    public void printSolution(){
        for(int i = 0; i < adjListArray.length; i++){
            System.out.print("(Vertex: " + i + "; Color: " + getColor(i) + ") ");
            if((i+1)%5 == 0){
                System.out.println();
            }
        }
        int colors = Arrays.stream(vColors).max().getAsInt();
        System.out.println("\nUsed "+colors+" colors.");
    }

    public int countColors(){
        return Arrays.stream(vColors).max().getAsInt();
    }
    public void color(int vertex, int color) {
        vColors[vertex] = color;
    }
    public int getColor(int vertex) {
        return vColors[vertex];
    }
    public LinkedList<Integer> getAdjacent(int vertex, Graph graph){
        return graph.adjListArray[vertex];
    }

    /**
     * Adding edge to directed graph
     * @param g
     * @param src
     * @param dest
     */
    public void addDirectedEdge(Graph g, int src, int dest) {
        directed = true;
        g.adjListArray[src].add(dest);
        edges++;
    }
}





