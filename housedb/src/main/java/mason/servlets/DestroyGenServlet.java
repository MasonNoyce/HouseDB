package mason.servlets;

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
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;
import mason.db.io.printhandler.PrintHandler;

public class DestroyGenServlet extends HttpServlet
{
    MyDatabase db;
    PrintHandler ph;

    ArrayList<MyHome> mhl;
    ArrayList<MyRoom> mrl;
    ArrayList<MyObject> mol;

    boolean tableFound = false;

    String stype = "dgs";

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        db = new MyDatabase();
        ph = new PrintHandler();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String type = req.getParameter("type");
        String home = req.getParameter("home");
        String room = req.getParameter("room");

        
        Statement stmt = null;

        switch(type)
        {

            case "objects":
                mol = new ArrayList<>();

                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM "+type+" WHERE home='"+home+"' AND room='"+room+"'");
                    while(rs.next())
                    {
                        MyObject mo = new MyObject();
                        mo.id = rs.getInt("id");
                        mo.room = rs.getString("room");
                        mo.home = rs.getString("home");
                        mo.object = rs.getString("object");
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
                //ph.printDestroyGen(resp,mol,home,room);
                
                break;


            case "rooms":
                mrl = new ArrayList<>();

                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rooms WHERE home='"+home+"'");
                    while(rs.next())
                    {
                        MyRoom mr = new MyRoom();
                        mr.id = rs.getInt("id");
                        mr.room = rs.getString("room");
                        mr.home = rs.getString("home");
                        mrl.add(mr);    
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
                //ph.printDestroyGen(resp,mrl,home);
                
                break;

            case "homes":
                mhl = new ArrayList<>();
                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + type);
                    while(rs.next())
                    {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.home = rs.getString("home");
                        mhl.add(mh); 
                        
                    }
                    System.out.println("Finished grabbing rows from homes");
                    System.out.println("mhl count: " + mhl.size());
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }
                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
                //ph.printDestroyGen(resp,mhl); 

                break;

            default:
                break;
        }
    }
    

    
}