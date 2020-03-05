package webpageReader;
import org.w3c.dom.NodeList;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class webPageReader {
    private String graphFileName;
    private String saveFolder;
    public webPageReader(String saveFolder) {
        this.saveFolder=saveFolder;

    }
    public void convert(String url) throws Exception {
        String myUrl = url;
        // if your url can contain weird characters you will want to
        // encode it here, something like this:
        // myUrl = URLEncoder.encode(myUrl, "UTF-8");

        String results = doHttpUrlConnectionAction(myUrl);
        String graphFileName = results.split("\\s")[2].replace(".col","");
        PrintWriter out = new PrintWriter("src\\"+saveFolder+"\\"+graphFileName);

        out.println(results);
    }
    private String doHttpUrlConnectionAction(String desiredUrl)
            throws Exception
    {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try
        {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setConnectTimeout(60*1000);
            connection.setReadTimeout(60*1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }
/*    public void convert(String url) {
        URL link = null;
        URLConnection yc = null;
        BufferedReader in = null;
        try {
            link = new URL(url);
            yc = link.openConnection();
            in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        File fout = null;
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != ""){
//                System.out.println(inputLine);
                if (inputLine.contains("c FILE:")){
                    graphFileName = inputLine.split("\\s")[2];
                    fout = new File("src\\"+saveFolder+"\\"+graphFileName+".txt");
                    fos = new FileOutputStream(fout);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                }
                if (inputLine.contains("p edge")){
                    bw.write(inputLine);
                    bw.newLine();
                    while ((inputLine = in.readLine()) != ""){
                        bw.write(inputLine);
                        bw.newLine();
                    }
                }
                bw.write(inputLine);
                bw.newLine();
            }
            in.close();
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
            System.out.println("error occured?");
        }
    }*/
}
