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

public class CreateGenServlet extends HttpServlet
{

    private ArrayList<MyHome> mhl;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //private static final long serialVersionUID = 1L;

        //make connection
        MyDatabase db = new MyDatabase();
        Statement stmt = null;
        mhl = new ArrayList<>();

        
        //get params
        String type = req.getParameter("type");
        String primaryKey = req.getParameter("name");



        switch(type)
        {
            case "homes":
                printHomes(resp);
                break;

            case "rooms":
                System.out.println("*************  "+primaryKey + "************");


                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM homes;");
                    while(rs.next())
                    {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.name = rs.getString("name");
                        mhl.add(mh);    
                    }
                    System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }   
                
                printRooms(resp,primaryKey);
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

        out.println("<form action='CreateHomeServlet' method='GET'");
        out.println("<h4>Create Home<h4><br>");
        out.println("<input type='text' name='name'>House Name<br>");
        out.println("<input type='hidden' name='type' value=homes'/>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");
    }

    private void printRooms(HttpServletResponse resp, String primaryKey) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Requested PRimary Key: "+primaryKey+"</p>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");

        out.println("<form action='CreateRoomServlet' method='GET'");
        out.println("<h4>Create Room<h4><br>");
        out.println("<input type='text' name='name'>Room Name<br>");
        out.println("<input type='hidden' name='primary' value='" + primaryKey + "'/>");
        out.println("<input type='hidden' name='type' value=rooms'/>");
        out.println("<input type='submit' name='submit' value='submit'>");
        out.println("</form>");

        out.close();
    }
}