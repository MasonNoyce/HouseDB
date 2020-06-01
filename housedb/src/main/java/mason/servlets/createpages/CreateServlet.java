package mason.servlets.createpages;

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

public class CreateServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    boolean debug = true;
    String name;
    String primary;
    String type;
    Statement stmt;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;

    DBOps dbops;

    @Override
    public void init() throws ServletException 
    {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        name = req.getParameter("name");
        primary = req.getParameter("primary");
        type = req.getParameter("type");

        fillStatement();

        ArrayList<String> valuens = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        System.out.println("***********Home SElected**********");
        System.out.println(type);

        switch(type)
        {
            case "homes":
                System.out.println("***********Home SElected**********");
                //Select columns to change
                valuens.add("name");

                //Select values to fill columns
                values.add("'"+name+"'");

                //Insert into table
                dbops.insertIntoTable(type, stmt,valuens,values);

                printHomes(resp);
                break;

            case "rooms":
                if(!dbops.checkTable(tables))dbops.createTable(type,stmt,dbops.getRoomParams());//if not create it

                //Select columns to change
                valuens.add("name");
                valuens.add("home");

                //Select values to fill columns
                values.add("'"+name+"'");
                values.add("'"+primary+"'");

                //Insert into table
                dbops.insertIntoTable(type, stmt,valuens,values);

                printRooms(resp);


                
                break;

            default:
                break;

        }

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

    private void printRooms(HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p> This is the name: " + name + "</p>");
        out.println("<p> This is the param: " + primary + "</p>");
        out.close();
    }

    private void printHomes(HttpServletResponse resp) throws IOException
    {
        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Home "+name+" was created</p>");
        out.close();

    }

}