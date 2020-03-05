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

        // File Name
        Random rand = new Random();


        // Contains the edges
        StringBuilder sb2= new StringBuilder("");

        for (int i = 1; i < V + 1; i++) {
            for (int j = i+1; j < V + 1; j++) {
                r = rand.nextFloat();
                if (r<=p){
                    edges++;
                    /*bw.write("e "+i+" "+j);
                    bw.newLine();*/
                    sb2.append("e"+" "+i+" "+j);
                    sb2.append(System.getProperty("line.separator"));
                }
            }
        }
        String fn = V+"-"+edges;
        //  Append fileName
        StringBuilder sb = new StringBuilder(fn);
        sb.append(System.getProperty("line.separator"));
        //  Append V and E
        sb.append("p edge"+" "+V+" "+edges);
        sb.append(System.getProperty("line.separator"));
        // Append edges
        sb.append(sb2.toString());

/*        PrintWriter out = new PrintWriter("src\\renyi\\"+fn);
            out.println(sb.toString());
            out.close();*/
        BufferedWriter out = new BufferedWriter(new FileWriter("src\\renyi\\"+fn));

//Add this to write a string to a file
//
        try {

            out.write(sb.toString());  //Replace with the string
            //you are trying to write
        }
        catch (IOException e)
        {
            System.out.println("Exception ");

        }
        finally
        {
            out.close();
        }
    }
}
