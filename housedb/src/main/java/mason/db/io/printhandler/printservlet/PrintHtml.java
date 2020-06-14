package mason.db.io.printhandler.printservlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.util.StreamReaderDelegate;

public class PrintHtml {
    public static PrintHtml instance = null;
    boolean debug = false;

    public static PrintHtml getInstance() {
        if (instance == null) {
            instance = new PrintHtml();

        }
        return instance;
    }

    public void printGen(HttpServletResponse resp,String filepath, PrintWriter out) {
        File file = new File(filepath);
        BufferedReader br;
        if(debug)System.out.println("printing html");
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            if(debug)System.out.println(line);

            while(line != null)
            {
//                System.out.println(line);
                out.write(line);
                line = br.readLine();
                if(debug)System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("unable to read header file");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Something went wront with reading the head file");
        }

        

        
    }


}