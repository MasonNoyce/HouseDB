package mason.servlets.htmlGenerators;

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

public class DestroyGenServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String type = req.getParameter("type");
        
        MyDatabase db = new MyDatabase();
        Statement stmt = null;

        switch(type)
        {
            case "rooms":
                ArrayList<MyRoom> mrl = new ArrayList<>();
                String primaryKey = req.getParameter("name");

                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rooms WHERE home='"+primaryKey+"'");
                    while(rs.next())
                    {
                        MyRoom mr = new MyRoom();
                        mr.id = rs.getInt("id");
                        mr.name = rs.getString("name");
                        mr.primary = rs.getString("home");
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
                printRooms(resp,mrl,primaryKey);
                
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
                        mh.name = rs.getString("name");
                        mhl.add(mh); 
                        
                    }
                    System.out.println("Finnished grabbing rows from homes");
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
            out.println("<p>" + mh.name + "  :  " + mh.id + "</p>");
        }

        out.println("<form action='DestroyServlet' method='GET'");
        out.println("<label for='homes'> Choose Home To Destroy</label>");
        out.println("<select id='homes' name='name'>");
        for(MyHome mh : mhl)
        {
            out.println("<option value='" + mh.name + "'>" + mh.name + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='type' value='homes'/>");

        out.println("<input type='submit'>");

        out.println("</form>");

        out.close();
    }

    private void printRooms(HttpServletResponse resp, ArrayList<MyRoom> mrl, String primaryKey) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");
        for(MyRoom mr : mrl)
        {
            out.println("<p>" + mr.name + "  :  " + mr.id + " : "+ mr.primary + "</p>");
        }

        out.println("<form action='DestroyServlet' method='GET'");
        out.println("<label for='rooms'> Choose Home To Destroy</label>");
        out.println("<select id='rooms' name='name'>");
        for(MyRoom mr : mrl)
        {
            out.println("<option value='" + mr.name + "'>" + mr.name + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='primary' value='" + primaryKey + "'/>");
        out.println("<input type='hidden' name='type' value='rooms'/>");

        out.println("<input type='submit'>");
        out.println("</form>");

        out.close();
    }
    
}