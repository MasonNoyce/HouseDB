package mason.servlets.createpages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.io.DBOps;

public class CreateRoomServlet extends HttpServlet 
{

     private static final long serialVersionUID = 1L;
    boolean debug = true;
    String name;
    String primary;
    Statement stmt;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;

    DBOps dbops;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        name = req.getParameter("name");
        primary = req.getParameter("primary");

        fillStatement();

        //Check if homes exist
        if(!dbops.checkTable(tables))dbops.createTable("rooms",stmt,dbops.getRoomParams());//if not create it

        //Select columns to change
        ArrayList<String> valuens = new ArrayList<String>();
        valuens.add("name");
        valuens.add("home");

        //Select values to fill columns
        ArrayList<String> values = new ArrayList<>();
        values.add("'"+name+"'");
        values.add("'"+primary+"'");

        //Insert into table
        dbops.insertIntoTable("rooms", stmt,valuens,values);


        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p> This is the name: " + name + "</p>");
        out.println("<p> This is the param: " + primary + "</p>");
        out.close();


        /*
        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        name = req.getprimary("name");

        fillStatement();
        //fill params
        ArrayList<String> params = new ArrayList<>();
        params.add("id serial PRIMARY KEY");
        params.add("name VARCHAR(50) UNIQUE");

        //Check if homes exist
        if(!dbops.checkTable(tables))dbops.createTable("home",stmt,params);//if not create it

        
        //Select columns to change
        ArrayList<String> valuens = new ArrayList<String>();
        valuens.add("name");

        //Select values to fill columns
        ArrayList<String> values = new ArrayList<>();
        values.add("'"+name+"'");

        //Insert into table
        dbops.insertIntoTable("homes", stmt,valuens,values);



        out.println("<a href=\"index.html\">Back</a>");
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>Home "+name+" was created</p>");
        // resp.getWriter().println(req.getprimary("name"));
        try {
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.close();
*/
    }


    private void fillStatement()
    {
        db = new MyDatabase();
        try 
        {
            dbm = db.getConnection().getMetaData();
            tables = dbm.getTables(null,null,"homes",null);
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