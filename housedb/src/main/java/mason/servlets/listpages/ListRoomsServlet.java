package mason.servlets.listpages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListRoomsServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         

        String name = req.getParameter("name");
        System.out.println(name);
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>This name was sent: "+name+"</p>");
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");

        out.close();
	}
}