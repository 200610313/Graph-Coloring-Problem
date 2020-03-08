package webpageReader;

import java.util.LinkedList;

public class Scraper {
    public void scrapeSGB() throws Exception {
        String folderName;
        folderName = "linksToSGB";
        urlReader urls = new urlReader(folderName);
        LinkedList<String> x = urls.getUrlsX();

        //Go to each url, read webpage, convert to graph text file
        for (int i = 0; i < x.size(); i++) {
            System.out.println("Reading "+x.get(i));
            new webPageReader(urls.getSaveFolder()).convert(x.get(i));
        }
    }
    public void scrapeCAR() throws Exception {
        String folderName;
        folderName = "linksToCAR";
        urlReader urls = new urlReader(folderName);
        LinkedList<String> x = urls.getUrlsX();

        //Go to each url, read webpage, convert to graph text file
        for (int i = 0; i < x.size(); i++) {
            System.out.println("Reading "+x.get(i));
            new webPageReader(urls.getSaveFolder()).convert(x.get(i));
        }
    }
}
