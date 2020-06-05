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

public class DestroyServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    String object;
    String room;
    String home;
    String type;

    boolean debug = true;

    Statement stmt;
    DatabaseMetaData dbm;
    MyDatabase db;
    ResultSet tables;

    DBOps dbops;

    ArrayList<MyHome> mhl;
    ArrayList<MyRoom> mrl;
    ArrayList<MyObject> mol;

    boolean tableFound = false;

    String stype = "ds";

    PrintHandler ph;


    @Override
    public void init() throws ServletException
    {
        super.init();
        dbops = new DBOps();
        db = new MyDatabase();
        
        mhl = new ArrayList<>();
        mrl = new ArrayList<>();
        mol = new ArrayList<>();

        ph = new PrintHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        System.out.println("I made it here");

        object = req.getParameter("object");
        room = req.getParameter("room");
        home = req.getParameter("home");
        type = req.getParameter("type");

        System.out.println("I made it here too");

        System.out.println(type);
        db.fillStatement(type);

        ArrayList<String> params;

        switch(type)
        {
            case "rooms":

                params = dbops.getRoomParams();

                break;
            case "homes":
                params = dbops.getHomeParams();
                break;

            case "objects":
                params = dbops.getObjParams();
                break;

            default:
                break;
        }
        System.out.println("Still Working******************");

//        if(!dbops.checkTable(db.getTables()))dbops.createTable(type, db.getStmt(), dbops.getRoomParams());//if not create it

        switch(type)
        {
            case "rooms":
                dbops.deleteFromTableByName(type, home,room, db.getStmt());
                break;
            case "homes":
                dbops.deleteFromTableByName(type, home, db.getStmt());
                break;

            case "objects":
                dbops.deleteFromTableByName(type, home, room,object, db.getStmt());
                break;
            default:
                break;
        }

        ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
        // createPage


    }

}