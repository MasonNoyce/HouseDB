package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintCreate {

        //for homes
        public void printCreate(HttpServletResponse resp, String home, PrintWriter out) throws IOException
        {
            // createPage
            resp.setContentType("text/html");
            out.println("<a href=\"index.html\">Back</a>");
            out.println("<p>Servlet Activated</p>");
            out.println("<p>Home "+home+" was created</p>");
    
        }
    
        //For Rooms
        public void printCreate(HttpServletResponse resp, String home, String room, PrintWriter out) throws IOException
        {
            resp.setContentType("text/html");
            out.println("<a href=\"index.html\">Back</a>");
            out.println("<p> This is the name: " + room + "</p>");
            out.println("<p> This is the param: " + home + "</p>");
        }
    
}