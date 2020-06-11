package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintCreateGen 
{
    private static PrintCreateGen instance;

    public static PrintCreateGen getInstance()
    {
        if(instance == null)
        {
            instance = new PrintCreateGen();
        }
        return instance;
    }

    //For Home
    public void printCreateGen(HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<form action='CreateServlet' method='GET'");
        out.println("<h4>Create Home<h4><br>");
        out.println("<input type='text' name='home'>House Name<br>");
        out.println("<input type='hidden' name='type' value='homes'>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");
    }

    //For Room
    public void printCreateGen(HttpServletResponse resp, String home) throws IOException
    {
        System.out.println("**********Room print Called************");

        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Requested PRimary Key: "+home+"</p>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<form action='CreateServlet' method='GET'");
        out.println("<h4>Create Room<h4><br>");
        out.println("<input type='text' name='room'>Room Name<br>");
        out.println("<input type='hidden' name='home' value='" + home + "'/>");
        out.println("<input type='hidden' name='type' value='rooms'>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");

        out.close();
    }


    //For Object
    public void printCreateGen(HttpServletResponse resp, String home, String room) throws IOException
    {
        System.out.println("**********Object print Called************");
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Requested PRimary Key: "+room+"</p>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<form action='CreateServlet' method='POST' enctype='multipart/form-data'");
        out.println("<h4>Create Room<h4><br>");
        out.println("<input type='text' name='object'>Object Name<br>");
        out.println("<input type='text' name='description'>description<br>");
        out.println("<input type='text' name='location'>location<br>");
        out.println("<input type='text' name='condition'>condition<br>");
        out.println("<input type='text' name='price'>price<br>");
        out.println("<input type='text' name='category'>category<br>");
        out.println("<input type='file' name='pic'>pic<br>");


        out.println("<input type='hidden' name='room' value='"+room+"'/>");
        out.println("<input type='hidden' name='home' value='"+home+"'/>");
        out.println("<input type='hidden' name='type' value='objects'>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");

        out.close();
    }

}