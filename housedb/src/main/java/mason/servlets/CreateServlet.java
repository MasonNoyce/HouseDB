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
import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;
import mason.db.io.DBOps;
import mason.db.io.printhandler.PrintHandler;

public class CreateServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    boolean debug = true;
    String object;
    String room;
    String home;

    String description;
    String location;
    String condition;
    String price;
    String category;
    String pic;

    String type;
    String stype = "cs";
    Statement stmt;

    ArrayList<MyHome> mhl;
    ArrayList<MyRoom> mrl;
    ArrayList<MyObject> mol;

    boolean tableFound = false;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;

    DBOps dbops;

    PrintHandler ph;

    @Override
    public void init() throws ServletException 
    {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();
        db = new MyDatabase();
        ph = new PrintHandler();

        mhl = new ArrayList<>();
        mrl = new ArrayList<>();
        mol = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        object = req.getParameter("object");
        room = req.getParameter("room");
        home = req.getParameter("home");
        type = req.getParameter("type");

        description = req.getParameter("description");
        location = req.getParameter("location");
        condition = req.getParameter("condition");
        price = req.getParameter("price");
        category = req.getParameter("category");
        pic = req.getParameter("pic");



        db.fillStatement(type);

        ArrayList<String> valuens = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        switch(type)
        {
            case "homes":
                //Select columns to change
                valuens.add("home");

                //Select values to fill columns
                values.add("'"+home+"'");

                //Insert into table
                dbops.insertIntoTable(type, db.getStmt(),valuens,values);

                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
//                ph.printCreate(resp,home);
                break;

            case "rooms":
                if(!dbops.checkTable(db.getTables()))dbops.createTable(type,db.getStmt(),dbops.getRoomParams());//if not create it

                //Select columns to change
                valuens.add("room");
                valuens.add("home");

                //Select values to fill columns
                values.add("'"+room+"'");
                values.add("'"+home+"'");

                //Insert into table
                dbops.insertIntoTable(type, db.getStmt(),valuens,values);

                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
//                ph.printCreate(resp,room);


                
                break;

            case "objects":
                if(!dbops.checkTable(db.getTables()))dbops.createTable(type,db.getStmt(),dbops.getRoomParams());//if not create it

                //Select columns to change
                valuens.add("room");
                valuens.add("home");
                valuens.add("object");
                valuens.add("description");
                valuens.add("location");
                valuens.add("condition");
                valuens.add("price");
                valuens.add("category");
                valuens.add("pic");

                //Select values to fill columns
                values.add("'"+room+"'");
                values.add("'"+home+"'");
                values.add("'"+object+"'");
                values.add("'"+description+"'");
                values.add("'"+location+"'");
                values.add("'"+condition+"'");
                values.add("'"+price+"'");
                values.add("'"+category+"'");
                values.add("'"+pic+"'");




                //Insert into table
                dbops.insertIntoTable(type, db.getStmt(),valuens,values);

                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
//                ph.printCreate(resp,object);


                
                break;

            default:
                break;

        }

    }




    private void printRooms(HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p> This is the name: " + room + "</p>");
        out.println("<p> This is the param: " + home + "</p>");
        out.close();
    }
   
    private void printHomes(HttpServletResponse resp) throws IOException
    {
        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Home "+home+" was created</p>");
        out.close();

    }

    private void printObjects(HttpServletResponse resp) throws IOException
    {
        // createPage
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Object "+object+" was created</p>");
        out.close();

    }

}