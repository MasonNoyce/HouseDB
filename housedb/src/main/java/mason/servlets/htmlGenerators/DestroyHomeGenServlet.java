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

public class DestroyHomeGenServlet extends HttpServlet
{

    private ArrayList<MyHome> mhl;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //private static final long serialVersionUID = 1L;

        //make connection
        MyDatabase db = new MyDatabase();
        Statement stmt = null;
        mhl = new ArrayList<>();


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

        out.println("<form action='DestroyHomeServlet' method='GET'");
        out.println("<label for='homes'> Choose Home To Destroy</label>");
        out.println("<select id='homes' name='name'>");
        for(MyHome mh : mhl)
        {
            out.println("<option value='" + mh.name + "'>" + mh.name + "</option>");
        }
        out.println("<input type='submit'>");
        out.println("</select>");
        out.println("</form>");

        out.close();

	}
}