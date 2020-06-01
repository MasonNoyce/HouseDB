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
import mason.db.interfaces.MyRoom;

public class DestroyRoomGenServlet extends HttpServlet
{

    private ArrayList<MyRoom> mrl;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //private static final long serialVersionUID = 1L;

        //make connection
        MyDatabase db = new MyDatabase();
        Statement stmt = null;
        mrl = new ArrayList<>();
        String primary = req.getParameter("name");


        try
        {
            stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rooms;");
            while(rs.next())
            {
                MyRoom mr = new MyRoom();
                mr.id = rs.getInt("id");
                mr.name = rs.getString("name");
                mr.primary = rs.getString("home");
                primary = mr.primary;
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

        out.println("<form action='DestroyRoomServlet' method='GET'");
        out.println("<label for='rooms'> Choose Home To Destroy</label>");
        out.println("<select id='rooms' name='name'>");
        for(MyRoom mr : mrl)
        {
            out.println("<option value='" + mr.name + "'>" + mr.name + "</option>");
        }
        out.println("</select>");
        out.println("<input type='hidden' name='primary' value='" + primary + "'/>");
        out.println("<input type='submit'>");
        out.println("</form>");

        out.close();

	}
}