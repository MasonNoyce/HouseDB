package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintDestroy {

    public void printDestroy(HttpServletResponse resp, PrintWriter out) throws IOException
    {
        // createPage
        resp.setContentType("text/html");
                
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Servlet Activated</p>");
        out.println("<p> was Destroyed</p>");

    }
}