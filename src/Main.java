import Brelaz.DSAT;
import FirstFit.FirstFit;
import Graph.Graph;
import MinDSAT.MinDsat;
import ReadFromDatabase.ReadFromDatabase;

import java.io.IOException;

public class Main {
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

        //ReadFromDatabase rfd = new ReadFromDatabase();

        //Test MinDsat

        //testMinDsat(rfd);

        //Test FirstFit
        //testFirstFit(rfd);

        //Test DSAT
        //testDSAT(rfd);
        new ErdosRenyi(50,0.5f);
    }

    private static void testDSAT(ReadFromDatabase rfd) {
        for(int i = 0; i < rfd.getFilesSize(); i++){
            Graph g = rfd.readFile(i);
            if(g == null){
                break;
            }
            new DSAT(g);
            System.out.println("DSAT used "+g.countColors());
        }
    }

    private static void testFirstFit(ReadFromDatabase rfd) {
        for(int i = 0; i < rfd.getFilesSize(); i++){
            Graph g = rfd.readFile(i);
            if(g == null){
                break;
            }
            new FirstFit(g);
            System.out.println("FirstFit used "+g.countColors());
        }
    }

    private static void testMinDsat(ReadFromDatabase rfd) {
        for(int i = 0; i < rfd.getFilesSize(); i++){
            Graph g = rfd.readFile(i);
            if(g == null){
                break;
            }
            new MinDsat(g);
            System.out.println("MinDsat used "+g.countColors());
        }
    }


}
