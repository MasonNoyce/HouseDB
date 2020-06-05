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
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p> was Destroyed</p>");
    }
}