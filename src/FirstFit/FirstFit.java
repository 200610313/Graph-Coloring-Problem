package FirstFit;
import Graph.Graph;
import java.util.LinkedList;

/**
 * TITLE: First Fit
 * DESCRIPTION: This class implements the first fit algorithm
 */

public class FirstFit {
    private Graph graph;

    /**
     * Constructor
     * @param g variable initialized to graph
     */
    public FirstFit(Graph g){
        this.graph = g;
        solve();
    }

    public void solve(){
        int vertices = graph.getV();
        for(int i = 0; i < vertices; i++){
            int color = findColor(i);
            graph.color(i , color);
        }
    }

    private int findColor(int vertex){
        int color, poppedVertex;
        if(vertex == 0){
            color = 1;
        }
        else {
            LinkedList<Integer> ala = new LinkedList<Integer>(graph.getAdjacent(vertex, graph));
            color = graph.getColor(vertex);
            if(ala.size() == 0){
                color = 1;
            }
            else {
                for (int i = 0; i < graph.getV(); i++) {
                    color = i + 1;
                    int tempColor = 1;
                    ala = new LinkedList<Integer>(graph.getAdjacent(vertex, graph));
                    for (int j = ala.size(); j > 0; j--) {
                        poppedVertex = ala.pop();
                        tempColor = graph.getColor(poppedVertex);
                        //System.out.println(vertex + ": " + color + " - " + tempColor);
                        if (tempColor == color) {
                            //System.out.println("break, same.");
                            break;
                        }
                    }
                    if(color != tempColor){
                        break;
                    }
                }
            }
        }
        return color;
    }
}
