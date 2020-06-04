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
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;

public class DestroyGenServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String type = req.getParameter("type");
        String home = req.getParameter("home");
        String room = req.getParameter("room");

        
        MyDatabase db = new MyDatabase();
        Statement stmt = null;

        switch(type)
        {

            case "objects":
                ArrayList<MyObject> mol = new ArrayList<>();

                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM "+type+" WHERE home='"+home+"' AND room='"+room+"'");
                    while(rs.next())
                    {
                        MyObject mo = new MyObject();
                        mo.id = rs.getInt("id");
                        mo.room = rs.getString("room");
                        mo.home = rs.getString("home");
                        mo.object = rs.getString("object");
                        mol.add(mo);    
                    }
                    System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }
                printObjects(resp,mol,home,room);
                
                break;


            case "rooms":
                ArrayList<MyRoom> mrl = new ArrayList<>();

                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rooms WHERE home='"+home+"'");
                    while(rs.next())
                    {
                        MyRoom mr = new MyRoom();
                        mr.id = rs.getInt("id");
                        mr.room = rs.getString("room");
                        mr.home = rs.getString("home");
                        mrl.add(mr);    
                    }
                    System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }
                printRooms(resp,mrl,home);
                
                break;

            case "homes":
                ArrayList<MyHome> mhl = new ArrayList<>();
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
                    System.out.println("Finished grabbing rows from homes");
                    System.out.println("mhl count: " + mhl.size());
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }
                printHomes(resp,mhl); 

                break;

            default:
                break;
        }
    }
    
    private void printHomes(HttpServletResponse resp, ArrayList<MyHome> mhl ) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
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

        out.close();
    }

    private void printRooms(HttpServletResponse resp, ArrayList<MyRoom> mrl, String home) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
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

        out.close();
    }

    private void printObjects(HttpServletResponse resp, ArrayList<MyObject> mol, String home,String room) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
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

        out.close();
    }
    
}