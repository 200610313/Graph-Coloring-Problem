package graph.generator;
import java.util.LinkedList;
import java.util.Random;
public class graphGenReyni {

    public LinkedList<Integer>[] getVertices() {
        return vertices;
    }

    private LinkedList<Integer>[] vertices;
    private int[] degrees;
    private float p;
    private int vCount;
    private Random r;
    /**
     * Constructor; generates random graph
     * @param numberOfVertices Total count of vertices in the graph
     */
    public graphGenReyni(int numberOfVertices, float p) {
        vertices = new LinkedList[numberOfVertices];
        degrees = new int[numberOfVertices];
        vCount=numberOfVertices;
        r=new Random();
        this.p=p;
        for (int i = 0; i < vCount; i++) {
            vertices[i] = new LinkedList<Integer>();
        }

        for (int i = 0; i < vCount; i++) {
            /*
            * Uncomment println print the graph*/
            //System.out.print(i);
            for (int j = i + 1; j < vCount; j++) {
                if (r.nextFloat() >= p){
                    //System.out.print(" -> " + j);
                    //Then we add an edge
                    vertices[i].add(j);

                    //We keep track of degrees of each vertices
                    degrees[i] = degrees[i] + 1;
                    degrees[j] = degrees[j] + 1;
                }
            }
            //System.out.println("");
            //printDegrees();
        }
        /*
        * At Max, we can only get vCount-Choose-2 vertices*/
    }
    public void printDegrees(){
        for (int i = 0; i < degrees.length; i++) {
            System.out.println("Degree of "+i+" :" +degrees[i]);
        }
    }
}
