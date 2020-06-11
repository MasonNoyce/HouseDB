package mason.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.InputStream;

import mason.db.MyDatabase;
import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;
import mason.db.io.DBOps;
import mason.db.io.printhandler.PrintHandler;

public class ObjectServlet extends HttpServlet
{
    boolean debug = true;

    MyDatabase db;
    DBOps dbops;

    String object;
    String room;
    String home;

    String type;

    ArrayList<MyHome> mhl;
    ArrayList<MyRoom> mrl;
    ArrayList<MyObject> mol;

    boolean tableFound = false;

    String stype = "os";
    
    PrintHandler ph;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        db = MyDatabase.getInstance();
        dbops = DBOps.getInstance();
        mol = new ArrayList<>();
        ph = PrintHandler.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        object = req.getParameter("object");
        room = req.getParameter("room");
        home = req.getParameter("home");

        type = req.getParameter("type");

        db.fillStatement(type);
        
        try
        {
            if(debug)System.out.println("I am trying to grab " + type);
            db.setStmt(db.getConnection().createStatement());
            ResultSet rs = db.getStmt().executeQuery("SELECT * FROM "+type+" WHERE home='"+home+"' AND room='"+room+"' AND object='"+object+"'");
            while(rs.next())
            {
                MyObject mo = new MyObject();
                mo.id = rs.getInt("id");
                mo.object = rs.getString("object");
                mo.room = rs.getString("room");
                mo.home = rs.getString("home");
                mo.description = rs.getString("description");
                mo.location = rs.getString("location");
                mo.condition = rs.getString("condition");
                mo.price = rs.getDouble("price");
                mo.category = rs.getString("category");
                mo.pic = rs.getString("pic");
                
                mol.add(mo);    
            }
            System.out.println("Finnished grabbing rows from homes");

        }
        catch (SQLException e) 
        {
            System.err.println("Something went wrong!");

            e.printStackTrace();
            return;
        }

        ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
    }

}