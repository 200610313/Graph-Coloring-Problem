import Brelaz.DSAT;
import FirstFit.FirstFit;
import Graph.Graph;
import MinDSAT.MinDsat;
import ReadFromDatabase.ReadFromDatabase;
import java.io.IOException;
import java.util.HashMap;



public class Main {
    private static int[] minDsat;
    private static double[] minDsatT;

    private static int[] FF;
    private static double[] ffT;

    private static int[] DSAT;
    private static double[] dsatT;

    public static void main(String[] args) throws IOException {
        // From my example
        //Graph g = new Graph(8);
        /*
        g.addEdge(0,1);
        g.addEdge(1,4);
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(2,5);
        g.addEdge(3,4);*/

        // From Adam Drozdek's
/*
        g.addEdge(g,0,4);
        g.addEdge(g,0,5);
        g.addEdge(g,0,6);
        g.addEdge(g,1,2);
        g.addEdge(g,1,4);
        g.addEdge(g,1,7);
        g.addEdge(g,2,6);
        g.addEdge(g,3,5);
        g.addEdge(g,3,6);
        g.addEdge(g,5,6);
        g.addEdge(g,5,7);
        g.addEdge(g,6,7);
        //new FirstFit(g);
        new MinDsat(g);
        //new DSAT(g);*/
        int N = 1000;
        minDsat = new int[N];
        minDsatT = new double[N];

        FF = new int[N];
        ffT = new double[N];

        DSAT = new int[N];
        dsatT = new double[N];

        ReadFromDatabase rfd = new ReadFromDatabase();


        //analyze(rfd,N);

        /*System.out.println("MinDSAT mode color used: "+ mode(minDsat));*/
        generateRenyi();
    }

    private static void generateRenyi() throws IOException {
        int start = 10;
        for (int i = 0; i < 10; i++) {
            new ErdosRenyi(start,(i+1)*0.1f);
            start=start+10;
        }
    }

    private static void analyze(ReadFromDatabase rfd, int N) {
        for (int i = 0; i < rfd.getFilesSize(); i++) {
            for (int j = 0; j < N; j++) {
                //Test MinDsat
                testMinDsat(rfd,i,j);

                //Test FirstFit
                testFirstFit(rfd,i,j);

                //Test DSAT
                testDSAT(rfd,i,j);
            }
            System.out.println("Filename: "+ rfd.getFN(i));
            System.out.println("MINDSAT COLOR: "+mode(minDsat));
            System.out.println("MINDSAT TIME: "+average(minDsatT,minDsatT.length));
            System.out.println("FIRSTFIT COLOR: "+mode(FF));
            System.out.println("FIRSTFIT TIME: "+average(ffT,ffT.length));
            System.out.println("DSAT COLOR: "+mode(DSAT));
            System.out.println("DSAT TIME: "+average(dsatT,dsatT.length));
            System.out.println("");
        }

    }


    static double avgRec(double a[], int i, int n)
    {
        // Last element
        if (i == n-1)
            return a[i];

        // When index is 0, divide sum computed so
        // far by n.
        if (i == 0)
            return ((a[i] + avgRec(a, i+1, n))/n);

        // Compute sum
        return (a[i] + avgRec(a, i+1, n));
    }

    // Function that return average of an array.
    static double average(double a[], int n)
    {
        return avgRec(a, 0, n);
    }
    public static int mode(int []array)
    {
        HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
        int max  = 1;
        int temp = 0;

        for(int i = 0; i < array.length; i++) {

            if (hm.get(array[i]) != null) {

                int count = hm.get(array[i]);
                count++;
                hm.put(array[i], count);

                if(count > max) {
                    max  = count;
                    temp = array[i];
                }
            }

            else
                hm.put(array[i],1);
        }
        return temp;
    }
    private static void testDSAT(ReadFromDatabase rfd, int fileIndex, int iteration) {
/*        for(int j = 0; j < rfd.getFilesSize(); j++){
            Graph g = rfd.readFile(j);
            if(g == null){
                break;
            }
            new DSAT(g);
            DSAT[i] = g.countColors();
            dsatT[i] = g.getTime();
        }*/
        Graph g = rfd.readFile(fileIndex);
        new DSAT(g);
        DSAT[iteration] = g.countColors();
        dsatT[iteration] = g.getTime();
    }

    public static void testFirstFit(ReadFromDatabase rfd, int fileIndex, int iteration) {
/*        for(int j = 0; j < rfd.getFilesSize(); j++){
            Graph g = rfd.readFile(j);
            if(g == null){
                break;
            }
            new FirstFit(g);
            FF[i] = g.countColors();
            ffT[i] = g.getTime();
        }*/
        Graph g = rfd.readFile(fileIndex);
        new FirstFit(g);
        FF[iteration] = g.countColors();
        ffT[iteration] = g.getTime();
    }

    private static void testMinDsat(ReadFromDatabase rfd, int fileIndex, int iteration) {
        /*for(int j = 0; j < rfd.getFilesSize(); j++){
            Graph g = rfd.readFile(j);
            if(g == null){
                break;
            }
            new MinDsat(g);
            minDsat[i] = g.countColors();
            minDsatT[i] = g.getTime();
        }*/
        Graph g = rfd.readFile(fileIndex);
        new MinDsat(g);
        minDsat[iteration] = g.countColors();
        minDsatT[iteration] = g.getTime();
    }


}
