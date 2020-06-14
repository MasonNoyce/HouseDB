package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintHome 
{
    private static PrintHome instance = null;

    boolean debug = false;
    public static PrintHome getInstance()
    {
        if(instance == null)
        {
            instance = new PrintHome();
        }
        return instance;
    }

            //for homes
    public void printHome(HttpServletResponse resp, PrintWriter out) throws IOException
    {
        //What I want
        //Section 1 Last Used Home
        //Section 2 Last 10 edited Objects
        //Section 3 Near Expired Groceries
        //Section 4 top 10 used Objects
        if(debug)System.out.println("<a href=\"index.html\">Back</a>");
        if(debug)System.out.println("<p>Servlet Activated</p>");
        if(debug)System.out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<a class='text-light' href=\"index.html\">Back</a><br>");
        out.println("<a class='text-light' href='ListServlet?type=homes'>All homes</a>");
        
        out.println("<p class='text-light'>Servlet Activated</p>");
        out.println("<p class='text-light'>Entered a fatal program, press return if you want to live</p>");
    }
    
}