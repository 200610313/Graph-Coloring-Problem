import java.io.*;
import java.util.Random;

/**
 * Generates unweighted, undirected, simple graphs.
 */
public class ErdosRenyi {
    private int V;
    private int edges;
    private float p;
    private float r;
    private int[] vertex;

    /**
     *
     * @param v number of vertices
     * @param p probability edge will connect two vertices
     */
    public ErdosRenyi(int v, float p) throws IOException {
        V = v;
        this.edges = 0;
        this.p = p;
        this.r = 1.00f;
        generate();
    }

    private void generate() throws IOException {
        Random rand = new Random();

        String fn = V+"-"+Integer.toString(rand.nextInt(100000));

        File fout = new File("src\\renyi\\"+fn);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.newLine();
        for (int i = 1; i < V; i++) {
            for (int j = i+1; j < V; j++) {
                r = rand.nextFloat();
                if (r<=p){
                    edges++;
                    bw.write("e "+i+" "+j);
                    bw.newLine();
                }
            }
        }
        bw.write("Count vertices: " +V+"\n"+"Count edges: "+edges);
        bw.close();
    }


}
