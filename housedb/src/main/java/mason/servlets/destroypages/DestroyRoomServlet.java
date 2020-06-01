package mason.servlets.destroypages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
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

public class DestroyRoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    String name;
    String primary;

    boolean debug = true;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {

        name = req.getParameter("name");
        primary = req.getParameter("primary");

        fillStatement();


        //Check if homes exist
        if(!dbops.checkTable(tables))dbops.createTable("rooms",stmt,dbops.getRoomParams());//if not create it
        

        //Select columns to change
        ArrayList<String> valuens = new ArrayList<String>();
        valuens.add("name");

        //Select values to fill columns
        ArrayList<String> values = new ArrayList<>();
        values.add("'"+name+"'");

        //Insert into table
//        dbops.insertIntoTable("homes", stmt,valuens,values);

        //Destroy from table
        dbops.deleteFromTableByName( "rooms", primary,name, stmt);

        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        
        out.println("<a href=\"index.html\">Back</a>");
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>Home "+name+" was Destroyed</p>");
        // resp.getWriter().println(req.getParameter("name"));
        out.close();

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