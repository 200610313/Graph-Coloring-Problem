package webpageReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Reads line by line one file as url then saves it as url strings
 */
public class urlReader {
    private String folderName;
    private File[] textGraph;// Each file in a folder is a graph
    private final File parentPath;
    private urlSaver us;
    private String saveFolder;

    public urlReader(String folderName) {
        this.folderName = folderName;
        this.parentPath = new File("src\\"+folderName);
        this.textGraph = parentPath.listFiles();

        if (folderName.equals("linksToSGB")){
            saveFolder = "SGB";
        }
        else if (folderName.equals("linksToCAR")){
            saveFolder = "CAR";
        }
        read();
    }

    private void read() {
        us = new urlSaver();
        for (int i = 0; i < textGraph.length; i++) {
            File input = new File(getPath(i));
            Scanner sc = null;
            try {
                sc = new Scanner(input);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                //  Read file line by line
                while (sc.hasNextLine()) {
                    us.addUrl(sc.nextLine());
                }
            }
        }
    }

    private String getPath(int index) {
        return textGraph[index].toString();
    }

    public LinkedList<String> getUrlsX() {
        return us.getUrls();
    }

    public String getSaveFolder() {
        return saveFolder;
    }

}
