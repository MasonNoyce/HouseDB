package mason.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyRoom;

public class CreateGenServlet extends HttpServlet
{

    private ArrayList<MyHome> mhl;
    private ArrayList<MyRoom> mrl;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //private static final long serialVersionUID = 1L;

        //make connection
        MyDatabase db = new MyDatabase();
        Statement stmt = null;
        mhl = new ArrayList<>();
        mrl = new ArrayList<>();

        boolean debug = true;

        
        //get params
        String type = req.getParameter("type");
        String home = req.getParameter("home");
        String room = req.getParameter("room");



        switch(type)
        {
            case "homes":
                printHomes(resp);
                break;

            case "rooms":
                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + type);
                    while(rs.next())
                    {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.home = rs.getString("home");
                        mhl.add(mh);    
                    }
                    if(debug)System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }   
                
                printRooms(resp,home);
                break;

            case "objects":
                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + type);
                    while(rs.next())
                    {
                        MyRoom mr = new MyRoom();
                        mr.id = rs.getInt("id");
                        mr.home = rs.getString("home");
                        mrl.add(mr);    
                    }
                    if(debug)System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }   
                
                printObjects(resp,home,room);
                break;

            default:
                break;
        }


    }
    

    private void printHomes(HttpServletResponse resp) throws IOException
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

    private void printRooms(HttpServletResponse resp, String home) throws IOException
    {
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

    private void printObjects(HttpServletResponse resp, String home, String room) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Requested PRimary Key: "+room+"</p>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<form action='CreateServlet' method='GET'");
        out.println("<h4>Create Room<h4><br>");
        out.println("<input type='text' name='object'>Object Name<br>");
        out.println("<input type='text' name='description'>description<br>");
        out.println("<input type='text' name='location'>location<br>");
        out.println("<input type='text' name='condition'>condition<br>");
        out.println("<input type='text' name='price'>price<br>");
        out.println("<input type='text' name='category'>category<br>");
        out.println("<input type='text' name='pic'>pic<br>");


        out.println("<input type='hidden' name='room' value='"+room+"'/>");
        out.println("<input type='hidden' name='home' value='" + home + "'/>");
        out.println("<input type='hidden' name='type' value='objects'>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");

        out.close();
    }

}