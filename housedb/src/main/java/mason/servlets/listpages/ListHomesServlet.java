package mason.servlets.listpages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.interfaces.MyHome;
import mason.db.io.DBOps;

public class ListHomesServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private ArrayList<MyHome> mhl;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;
    Statement stmt = null;
    DBOps dbops;

    boolean debug = true;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
        //make connection

        mhl = new ArrayList<>();
        System.out.println("TEST");
        System.out.println(dbops.test);
//System.out.println(dbops.checkTable(tables));

        fillStatement();
        if(!dbops.checkTable(tables))dbops.createTable("homes",stmt,dbops.getHomeParams());//if not create it


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
        
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");
        //Create links for homes
        for(MyHome mh : mhl)
        {
            resp.getWriter().println("<a href=ListRoomsServlet?name="+mh.name + ">" + mh.name + "</a><br>");
        }

        out.println("<br><br><a href='pages/createhome.html'>Create Home</a><br><a href='DestroyHomeGenServlet'>Destroy Home</a>");

        out.close();

    }
    
    private void fillStatement()
    {
        if(debug)System.out.println("Filling Statements");
        db = new MyDatabase();
        try 
        {
            dbm = db.getConnection().getMetaData();
            tables = dbm.getTables(null,null,"homes",null);
            stmt = db.getConnection().createStatement();
            if(debug)System.out.println("Statements Filled");
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to connect to metadata");
        }

    }
}