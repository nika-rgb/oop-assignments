import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private int rowIndex;
    private webFrame frame;
    private String urlString;

    public WebWorker(String url, int rowIndex, webFrame frame){
        this.urlString = url;
        this.rowIndex = rowIndex;
        this.frame = frame;
    }

    @Override
    public void run(){
        boolean increased = false;
        try {
            Thread.sleep(100);
            frame.increaseRunningThreads(rowIndex);
            increased = true;
            if(isInterrupted()) frame.changeTable(rowIndex, "interrupted");
            if(!isInterrupted()) download();
        } catch (InterruptedException e) {
            frame.changeTable(rowIndex, "interrupted");
        }finally {
            frame.releaseThread(rowIndex, increased);
        }
    }


  //This is the core web/download i/o code...
 	private void download() throws InterruptedException{
        InputStream input = null;
        StringBuilder contents = null;
        try {
            long start = System.nanoTime();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            if(isInterrupted()){
                frame.changeTable(rowIndex, "interrupted");
                return;
            }

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                Thread.sleep(100);
            }

            if(isInterrupted()) {
                frame.changeTable(rowIndex, "interrupted");
                return;
            }

            // Successful download if we get here
            Thread.sleep(100);
            long end = System.nanoTime();
            DateFormat format = new SimpleDateFormat();
            String formatted = format.format(end);
            StringBuilder builder =  new StringBuilder();
            builder.append(formatted).append(" ").append((end - start) / 1000000).append(" ms ").append(contents.length()).append(" bytes");


            if(!isInterrupted())
                frame.changeTable(rowIndex, builder.toString());
            else frame.changeTable(rowIndex, "interrupted");
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            if(isInterrupted()) {
                frame.changeTable(rowIndex, "interrupted");
                return;
            }
            frame.changeTable(rowIndex, "err");
        }  catch (IOException ignored) {
            if(isInterrupted()) {
                frame.changeTable(rowIndex, "interrupted");
                return;
            }
            frame.changeTable(rowIndex, "err");
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
                if(isInterrupted()){
                    frame.changeTable(rowIndex, "interrupted");
                    return;
                }
                frame.changeTable(rowIndex, "err");
            }
        }
    }


	
}
