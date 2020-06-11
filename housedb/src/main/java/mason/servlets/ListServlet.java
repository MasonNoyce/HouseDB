package mason.servlets;

import java.sql.Statement;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class ListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // private ArrayList<MyHome> mhl;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;
    Statement stmt = null;
    DBOps dbops;
    PrintHandler ph;

    boolean debug = true;

    String stype = "ls";//listservlet
    String type;
    String home;
    String room;
    boolean tableFound = false;

    ArrayList<MyRoom> mrl;
    ArrayList<MyObject> mol;
    ArrayList<MyHome> mhl;


    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = DBOps.getInstance();
        db = MyDatabase.getInstance();
        ph = PrintHandler.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        type = req.getParameter("type");
        home = req.getParameter("home");
        room = req.getParameter("room");

        db.fillStatement(type);
        tableCheck(type);

        switch (type) {
            case "homes":
                if (debug)
                    System.out.println("I am in Homes");

                // get Rid of this in destroy and create

                mhl = new ArrayList<>();
                try {
                    if (debug)
                        System.out.println("I am trying to grab " + type);
                    db.setStmt(db.getConnection().createStatement());
                    ResultSet rs = db.getStmt().executeQuery("SELECT * FROM " + type);
                    while (rs.next()) {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.home = rs.getString("home");
                        mhl.add(mh);
                    }
                    System.out.println("Finnished grabbing rows from homes");

                } catch (SQLException e) {
                    System.err.println("Something went wrong!");

                    e.printStackTrace();
                    return;
                }
                // printHomes(resp, mhl);
//                ph.printList(resp, mhl);
                ph.printResponse(resp,mhl,mrl,mol,tableFound,home,room,type,stype);                
                break;

            case "rooms":

                mrl = new ArrayList<>();
                tableFound = false;
                try {
                    db.setStmt(db.getConnection().createStatement());
                    ResultSet rs = db.getStmt().executeQuery("SELECT * FROM rooms WHERE home = '" + home + "'");

                    if (rs.next()) {
                        tableFound = true;
                        do {
                            MyRoom mr = new MyRoom();
                            mr.id = rs.getInt("id");
                            mr.room = rs.getString("room");
                            mr.home = rs.getString("home");
                            mrl.add(mr);
                        } while (rs.next());

                        if (debug)
                            System.out.println("Finnished grabbing rows from rooms");
                    } else {

                        if (debug)
                            System.out.println("No Rooms Found");
                        tableFound = false;
                    }

                } catch (SQLException e) {
                    System.err.println("Something went wrong!");

                    e.printStackTrace();
                    return;
                }
                ph.printResponse(resp,mhl,mrl,mol,tableFound,home,room,type,stype);                


//                ph.printList(resp, mrl, tableFound, home);

                break;

            case "objects":

                mol = new ArrayList<>();
                tableFound = false;
                try {

                    db.setStmt(db.getConnection().createStatement());

                    System.out.println(room);
                    ResultSet rs = db.getStmt().executeQuery(
                            "SELECT * FROM objects WHERE home = '" + home + "' AND room = '" + room + "'");

                    if (rs.next()) {

                        tableFound = true;
                        do {
                            MyObject mo = new MyObject();
                            mo.id = rs.getInt("id");
                            mo.object = rs.getString("object");
                            mo.room = rs.getString("room");
                            mo.home = rs.getString("home");
                            mo.description = rs.getString("description");
                            mo.location = rs.getString("location");
                            mo.price = rs.getDouble("price");
                            mo.category = rs.getString("category");
                            mo.pic = rs.getString("pic");

                            mol.add(mo);

                        } while (rs.next());

                        if (debug)
                            System.out.println("Finnished grabbing rows from rooms");
                    } else {

                        if (debug)
                            System.out.println("No Objects Found");
                        tableFound = false;
                    }

                } catch (SQLException e) {
                    System.err.println("Something went wrong!");

                    e.printStackTrace();
                    return;
                }

                ph.printResponse(resp,mhl,mrl,mol,tableFound,home,room,type,stype);                

//                ph.printList(resp, mol, otableFound, home, room);

                break;

            default:
                break;
        }

    }

    public void tableCheck(String type) {
        switch (type) {
            case "homes":
                if (!dbops.checkTable(db.getTables()))
                    dbops.createTable(type, db.getStmt(), dbops.getHomeParams());// if not create it
                break;
            case "rooms":
                if (!dbops.checkTable(db.getTables()))
                    dbops.createTable(type, db.getStmt(), dbops.getRoomParams());// if not create it
                break;
            case "objects":
                if (!dbops.checkTable(db.getTables()))
                    dbops.createTable(type, db.getStmt(), dbops.getObjParams());// if not create it
                break;

        }
    }
}