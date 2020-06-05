package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;

public class PrintDestroyGen {

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyHome> mhl, PrintWriter out) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        out.println("<a href=\"index.html\">Back</a>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");
        for(MyHome mh : mhl)
        {
            out.println("<p>" + mh.home + "  :  " + mh.id + "</p>");
        }

        out.println("<form action='DestroyServlet' method='GET'");
        out.println("<label for='homes'> Choose Home To Destroy</label>");
        out.println("<select id='homes' name='home'>");
        for(MyHome mh : mhl)
        {
            out.println("<option value='" + mh.home + "'>" + mh.home + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='type' value='homes'/>");

        out.println("<input type='submit'>");

        out.println("</form>");

    }

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyRoom> mrl, String home, PrintWriter out) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        out.println("<a href=\"index.html\">Back</a>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");
        for(MyRoom mr : mrl)
        {
            out.println("<p>" + mr.room + "  :  " + mr.id + " : "+ mr.home + "</p>");
        }

        out.println("<form action='DestroyServlet' method='GET'");
        out.println("<label for='rooms'> Choose Home To Destroy</label>");
        out.println("<select id='rooms' name='room'>");
        for(MyRoom mr : mrl)
        {
            out.println("<option value='" + mr.room + "'>" + mr.room + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='home' value='" + home + "'/>");
        out.println("<input type='hidden' name='type' value='rooms'/>");

        out.println("<input type='submit'>");
        out.println("</form>");

    }

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyObject> mol, String home,String room,PrintWriter out) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        out.println("<a href=\"index.html\">Back</a>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");
        for(MyObject mo : mol)
        {
            out.println("<p>" +mo.object + "   :   "+ mo.room + "  :  " + mo.id + " : "+ mo.home + "</p>");
        }

        out.println("<form action='DestroyServlet' method='GET'");
        out.println("<label for='objects'> Choose Home To Destroy</label>");
        out.println("<select id='objects' name='object'>");
        for(MyObject mo : mol)
        {
            out.println("<option value='" + mo.object + "'>" + mo.object + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='home' value='" + home + "'/>");
        out.println("<input type='hidden' name='room' value='" + room + "'/>");

        out.println("<input type='hidden' name='type' value='objects'/>");

        out.println("<input type='submit'>");
        out.println("</form>");

    }
    
}