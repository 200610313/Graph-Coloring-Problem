package ReadFromDatabase;

import Graph.Graph;

import java.io.File;
import java.util.Scanner;
public class ReadFromDatabase {
    private File files[];
    private int filesRead;

    public ReadFromDatabase(){
        //change paren
        File parentPath = new File("src\\queen");
        files = parentPath.listFiles();
        filesRead = 0;
    }

    public Graph readFile(int i){
        File input = new File(getPath(i));
        int numberOfVertices = 0;
        try {
            Scanner sc = new Scanner(input);
            while (sc.hasNextLine()) {
                if (sc.next().equals("p")) {
                    sc.next();
                    numberOfVertices = sc.nextInt();
                    sc.nextLine();
                    break;
                }
            }

            Graph g = new Graph(numberOfVertices);
            int vertex1 = 0, vertex2 = 0;
            while (sc.hasNextLine() && sc.hasNext("e")) {
                //sc.nextLine();
                if (sc.next().equals("e")) {
                    vertex1 = sc.nextInt() - 1;
                    vertex2 = (sc.nextInt()) - 1;
                    g.addEdge(g, vertex1, vertex2);
                    //System.out.println("Retrieved: " + vertex1 + " " + vertex2);
                }
            }
            return g;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String getPath(int index) {
        return files[index].toString();
    }

    public int getFilesSize(){
        return files.length;
    }
}