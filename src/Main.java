import Brelaz.DSAT;
import FirstFit.FirstFit;
import Graph.Graph;
import MinDSAT.MinDsat;
import textToGraph.ReadFromDatabase;
import webpageReader.Scraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static int[] minDsat;// <- Stores the approximation results of each vertices
    private static double[] minDsatT;// <- And the time taken to finish the algorithm
                                    // Size of the array = total number of iterations
    private static int[] FF;
    private static double[] ffT;

    private static int[] DSAT;
    private static double[] dsatT;

    private static String folder;
    private static Scanner sc;

    private static ReadFromDatabase rfd;
    private static double start, finish;


    private static int vSGB, vCAR, vRenyi;//  Holds the current vertex size
    private static int eSGB, eCAR, eRenyi;//  And the number of edges
                                          //  of the respective graphs
    private static int N;
    public static void main(String[] args) throws IOException {

        // G1
        /*Graph g = new Graph(8);

        g.addEdge(g,0,1);
        g.addEdge(g,1,4);
        g.addEdge(g,1,2);
        g.addEdge(g,2,3);
        g.addEdge(g,2,5);
        g.addEdge(g,3,4);*/

        // G2

      /*  g.addEdge(g,0,4);*/
      /*  g.addEdge(g,0,5);*/
      /*  g.addEdge(g,0,6);*/
      /*  g.addEdge(g,1,2);*/
      /*  g.addEdge(g,1,4);*/
      /*  g.addEdge(g,1,7);*/
      /*  g.addEdge(g,2,6);*/
      /*  g.addEdge(g,3,5);*/
      /*  g.addEdge(g,3,6);*/
      /*  g.addEdge(g,5,6);*/
      /*  g.addEdge(g,5,7);*/
      /*  g.addEdge(g,6,7);*/
        //new FirstFit(g);
        //new MinDsat(g);
/*        new DSAT(g);

        int x = g.countColors();
        System.out.println(x);*/
        sc = new Scanner(System.in);
        N = 1000;
        minDsat = new int[N];
        minDsatT = new double[N];

        FF = new int[N];
        ffT = new double[N];

        DSAT = new int[N];
        dsatT = new double[N];
//        ReadFromDatabase rfd = new ReadFromDatabase("CAR");

        showmenu();
        //analyze(rfd,N);
        /*System.out.println("MinDSAT mode color used: "+ mode(minDsat));*/
        //generateRenyi();
    }

    private static void showmenu() {
        int input = 0;
        boolean exitprogram = false;
        rfd = new ReadFromDatabase("CAR",0);
        do {
            System.out.println("Input Graph = "+rfd.getGraphType());
            System.out.println("N = "+N);
            System.out.print("[0] Change Input Graph\n[1] Benchmark\n[2] Generate SGB Graphs\n[3] Generate CAR Graphs\n[4] Generate Erdos-Renyi\n[5] Quit\n[6] Change Iteration\n");
            input = sc.nextInt();
            if (input == 0){
                showGraphChoices();
            }
            else if (input == 1){
                analyze(rfd, N);
            }
            else if (input == 2){
                try {
                    new Scraper().scrapeSGB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (input == 3){
                try {
                    new Scraper().scrapeCAR();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (input == 4){
                System.out.println("Count = ");
                int n = sc.nextInt();
                System.out.println("p = ");
                float p = sc.nextFloat();
                try {
                    generateRenyi(n,p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (input == 5){
                exitprogram = true;
            }
            else if (input == 6){
                N = sc.nextInt();
            }
            else {
                showmenu();
            }
        }while (!exitprogram);
    }


    private static void showGraphChoices() {
        System.out.print("\n[0] CAR\n[1] SGB\n[2] Erdos-Renyi\n");
        int input = sc.nextInt();
        if (input == 0){

            rfd = new ReadFromDatabase("CAR",0);
        }
        else if (input == 1){
            rfd = new ReadFromDatabase("SGB",1);
        }
        else if (input == 2){
            rfd = new ReadFromDatabase("renyi",0);
        }
        else {
            return;
        }
    }

    private static void generateRenyi(int n, float p) throws IOException {
        int start = 10;
        for (int i = 0; i < n; i++) {
            ErdosRenyi er = new ErdosRenyi(start,(i+1)*p);
            start=start+10;
        }
    }

    private static void analyze(ReadFromDatabase rfd, int N) {
        start = System.nanoTime();
        for (int i = 0; i < rfd.getFilesSize(); i++) {
            for (int j = 0; j < N; j++) {
                //Test MinDsat
                testMinDsat(rfd,i,j);

                //Test FirstFit
                testFirstFit(rfd,i,j);

                //Test DSAT
                testDSAT(rfd,i,j);
            }
            finish = System.nanoTime();

            String graphType = rfd.getGraphType(),
                    fileName = rfd.getFN(i);
            int vSize = vSGB,
                    eCount = eCAR,
                    cMDSAT = mode(minDsat),cFF = mode(FF),cDSAT = mode(DSAT);

            double tMDSAT = average(minDsatT, minDsatT.length),
                    tFF = average(ffT,ffT.length),
                    tDSAT = average(dsatT,dsatT.length);


            new excelWriter(graphType,fileName,vSize,eCount,cMDSAT,tMDSAT,cFF,tFF,cDSAT, tDSAT, i);


            System.out.println("\nGraph Type: "+rfd.getGraphType());
            System.out.println("Filename: "+ rfd.getFN(i));
            System.out.println("Vertex Size: "+vSGB);
            System.out.println("Edge Count: "+eCAR);
            System.out.println("\nMINDSAT COLOR: "+mode(minDsat));
            System.out.println("MINDSAT TIME: "+average(minDsatT,minDsatT.length));
            System.out.println("\nFIRSTFIT COLOR: "+mode(FF));
            System.out.println("FIRSTFIT TIME: "+average(ffT,ffT.length));
            System.out.println("\nDSAT COLOR: "+mode(DSAT));
            System.out.println("DSAT TIME: "+average(dsatT,dsatT.length));


        }
        double secondsElapsed = (double)(finish-start)/1000000000;
        System.out.println("\nBenchmark ended after "+secondsElapsed+" seconds.");
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
        Graph g = rfd.readFile(fileIndex);
        new DSAT(g);
        identifyVerticesAndEdges(rfd,g);
        DSAT[iteration] = g.countColors();
        dsatT[iteration] = g.getTime();
    }

    private static void identifyVerticesAndEdges(ReadFromDatabase rfd, Graph g) {
        if (rfd.getGraphType().equals("CAR"));{
            vCAR = g.getV();
            eCAR = g.getEdges();
        }
        if (rfd.getGraphType().equals("SGB"));{
            vSGB = g.getV();
            eSGB = g.getEdges();
        }
        if (rfd.getGraphType().equals("renyi"));{
            vRenyi = g.getV();
            eRenyi = g.getEdges();
        }
    }

    public static void testFirstFit(ReadFromDatabase rfd, int fileIndex, int iteration) {
        Graph g = rfd.readFile(fileIndex);
        new FirstFit(g);
        identifyVerticesAndEdges(rfd,g);
        FF[iteration] = g.countColors();
        ffT[iteration] = g.getTime();
    }

    private static void testMinDsat(ReadFromDatabase rfd, int fileIndex, int iteration) {
        Graph g = rfd.readFile(fileIndex);
        new MinDsat(g);
        identifyVerticesAndEdges(rfd,g);
        minDsat[iteration] = g.countColors();
        minDsatT[iteration] = g.getTime();
    }
}
