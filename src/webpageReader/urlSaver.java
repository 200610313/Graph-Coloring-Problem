package webpageReader;
import java.util.LinkedList;

public class urlSaver {
    //  Store links in array list
    private LinkedList<String> urls;
    public urlSaver()
    {
        this.urls = new LinkedList<>();
    }

    public void addUrl(String url){
        try {
            urls.add(url);
        }
        catch (Exception e){
        }
        finally {
            System.out.println(url+" has been added to urls.");
        }
    }

    public LinkedList<String> getUrls() {
        return this.urls;
    }
}
