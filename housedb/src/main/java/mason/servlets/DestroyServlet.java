package mason.servlets;

import java.sql.Statement;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.io.DBOps;

public class DestroyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    String room;
    String home;
    String type;

    boolean debug = true;

    Statement stmt;
    DatabaseMetaData dbm;
    MyDatabase db;
    ResultSet tables;

    DBOps dbops;


    @Override
    public void init() throws ServletException
    {
        super.init();
        dbops = new DBOps();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        room = req.getParameter("room");
        home = req.getParameter("home");
        type = req.getParameter("type");

        fillStatement();

        ArrayList<String> params;
        switch(type)
        {
            case "rooms":
                params = dbops.getRoomParams();
                break;
            case "homes":
                params = dbops.getHomeParams();
                break;

            default:
                break;
        }
        if(!dbops.checkTable(tables))dbops.createTable(type,stmt,dbops.getRoomParams());//if not create it

        switch(type)
        {
            case "rooms":
                dbops.deleteFromTableByName(type, home,room, stmt);
                break;
            case "homes":
                dbops.deleteFromTableByName(type, home, stmt);
                break;
            default:
                break;
        }

        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
                
        out.println("<a href=\"index.html\">Back</a>");
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>" + type +" " + room +" was Destroyed</p>");
        out.close();

    }


    private void fillStatement()
    {
        db = new MyDatabase();
        try 
        {
            dbm = db.getConnection().getMetaData();
            tables = dbm.getTables(null,null,type,null);
            stmt = db.getConnection().createStatement();
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to connect to metadata");
        }

    }

}