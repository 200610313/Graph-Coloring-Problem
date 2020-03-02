package customgraph;
import java.util.LinkedList;
//Contributed by Sumit Ghosh, GeeksForGeeks
//Graph implementation : Undirected, unweighted, LinkedList
public class Graph {
    private int V;
    private LinkedList<Integer> adjListArray[];
    private int[] vColors; // Stores the colors of the vertices

    public int[] getvColors() {
        return vColors;
    }

    // constructor
    public Graph(int V)
    {
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
    public void addEdge(int src, int dest)
    {
        // Add an edge from src to dest.
        adjListArray[src].add(dest);

        // Since text2Graph is undirected, add an edge from dest
        // to src also
        adjListArray[dest].add(src);
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

    public void color(int vertex, int color) {
        vColors[vertex] = color;
    }
}





