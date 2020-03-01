package customgraph;

public class MatrixGraph {
    private int V;
    private int[][] graph;
    private int[] vColors;

    public int getV() {
        return V;
    }

    public int[][] getGraph() {
        return graph;
    }

    public MatrixGraph(int V){
        this.V = V;
        this.graph = new int[V][V];
        this.vColors = new int[V];
    }

    public void addEdge(int src, int dest){
        graph[src][dest] = 1;
        graph[dest][src] = 1;
    }

    public void deleteEdge(int src, int dest){
        graph[src][dest] = 0;
        graph[dest][src] = 0;
    }

    // First color must be colored 1
    public void color(int V, int color) {
        vColors[V] = color;
    }

}
