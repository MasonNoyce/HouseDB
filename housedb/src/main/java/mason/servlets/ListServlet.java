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

public class ListServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

 //   private ArrayList<MyHome> mhl;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;
    Statement stmt = null;
    DBOps dbops;

    boolean debug = true;

    String type;
    String home;
    String room;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();
        db = new MyDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        type = req.getParameter("type");
        home = req.getParameter("home");
        room = req.getParameter("room");

        db.fillStatement(type);
        tableCheck(type);

        switch(type)
        {
            case "homes":
                if(debug)System.out.println("I am in Homes");

                //get Rid of this in destroy and create
        
                ArrayList<MyHome> mhl = new ArrayList<>();
                try
                {
                    if(debug)System.out.println("I am trying to grab " + type);
                    db.setStmt(db.getConnection().createStatement());
                    ResultSet rs = db.getStmt().executeQuery("SELECT * FROM " + type);
                    while(rs.next())
                    {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.home = rs.getString("home");
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
                printHomes(resp, mhl);
                break;

            case "rooms":

                ArrayList<MyRoom> mrl = new ArrayList<>();
                boolean tableFound = false;
                try
                {        
                    db.setStmt(db.getConnection().createStatement());        
                    ResultSet rs = db.getStmt().executeQuery("SELECT * FROM rooms WHERE home = '"+home+"'");
        
                    if(rs.next())
                    {        
                        tableFound = true;
                        do
                        {        
                            MyRoom mr = new MyRoom();
                            mr.id = rs.getInt("id");
                            mr.room = rs.getString("room");
                            mr.home = rs.getString("home");
                            mrl.add(mr);    
                        }while(rs.next());
        
                        if(debug)System.out.println("Finnished grabbing rows from rooms");
                    }
                    else
                    {
        
                        if(debug)System.out.println("No Rooms Found");
                        tableFound = false;
                    }
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
        
                    e.printStackTrace();
                    return;
                }

                printRooms(resp,mrl,tableFound);
    

                break;

            case "objects":

                ArrayList<MyObject> mol = new ArrayList<>();
                boolean otableFound = false;
                try
                { 
       
                    db.setStmt(db.getConnection().createStatement());

                    System.out.println(room);
                    ResultSet rs = db.getStmt().executeQuery("SELECT * FROM objects WHERE home = '"+home+"' AND room = '"+room+"'");
        
                    if(rs.next())
                    {
        
                        otableFound = true;
                        do
                        {        
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

                        }while(rs.next());
        
                        if(debug)System.out.println("Finnished grabbing rows from rooms");
                    }
                    else
                    {
        
                        if(debug)System.out.println("No Objects Found");
                        otableFound = false;
                    }
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
        
                    e.printStackTrace();
                    return;
                }

                printObjects(resp,mol,otableFound);
                
                break;

            default:
                break;
        }

    }



    private void printHomes(HttpServletResponse resp, ArrayList<MyHome> mhl) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"index.html\">Back</a>");
        
        resp.getWriter().println("<p>Servlet Activated</p>");
        resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");
        //Create links for homes
        for(MyHome mh : mhl)
        {
            resp.getWriter().println("<a href=ListServlet?home="+mh.home + "&type=rooms>" + mh.home + "</a><br>");
        }

        out.println("<br><br><a href='CreateGenServlet?type=homes'>Create Home</a><br><a href='DestroyGenServlet?type=homes'>Destroy Home</a>");

        out.close();        
    }

    private void printRooms(HttpServletResponse resp, ArrayList<MyRoom> mrl, boolean tableFound)
            throws IOException
    {
        PrintWriter out = resp.getWriter();

        if(tableFound)
        {
            System.out.println("Creating Room Link");

            //Display List as links in html format
            resp.setContentType("text/html");
            out.println("<a href=\"index.html\">Back</a>");
            
            resp.getWriter().println("<p>Servlet Activated</p>");
            resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");
            //Create links for homes
            for(MyRoom mr : mrl)
            {
                System.out.println("Creating Room Link");
                resp.getWriter().println("<a href=ListServlet?room="+mr.room + "&home=" +mr.home+ "&type=objects>" + mr.room + 
                "</a><br>");
            }

            out.println("<br><br><a href='CreateGenServlet?home="+home+
            "&type=objects'>Create Object</a><br><a href='DestroyGenServlet?home="+
            home+"&type=objects'>Destroy Object</a>");

        }       
        else
        {
            out.println("<p> No Rooms Found</p>");
            out.println("<p>Requested Key: " + home+"</p>");

            out.println("<br><br><a href='CreateGenServlet?home="+home+
            "&type=objects'>Create Object</a><br><a href='DestroyGenServlet?home="+
            home+"&type=objects'>Destroy Object</a>");

            

        }
        out.close();

    }



    private void printObjects(HttpServletResponse resp, ArrayList<MyObject> mol, boolean tableFound)
            throws IOException
    {
        PrintWriter out = resp.getWriter();

        if(tableFound)
        {
            System.out.println("Creating Object Link");

            //Display List as links in html format
            resp.setContentType("text/html");
            out.println("<a href=\"index.html\">Back</a>");
            
            resp.getWriter().println("<p>Servlet Activated</p>");
            resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");
            //Create links for homes
            for(MyObject mo : mol)
            {
                System.out.println("Creating ObjectLink Link");

                resp.getWriter().println("<a href=ObjectServlet?object="+mo.object+"&room="+mo.room +
                 "&home=" +mo.home+ "&type=objects>" + mo.object + "</a><br>");
            }

            out.println("<br><br><a href='CreateGenServlet?room="+room+"&home="+home+
            "&type=objects'>Create Object</a><br><a href='DestroyGenServlet?room="+room+"&home="+
            home+"&type=objects'>Destroy Object</a>");

        }       
        else
        {
            out.println("<p> No Objects Found</p>");
            out.println("<p>Requested Key: " + home +"</p>");

            out.println("<br><br><a href='CreateGenServlet?room="+room+"&home="+home+
            "&type=objects'>Create Object</a><br><a href='DestroyGenServlet?room="+room+"home="+
            home+"&type=objects'>Destroy Object</a>");

            

        }
        out.close();

    }

    public void tableCheck(String type)
    {
        switch(type)
        {
            case "homes":
                if(!dbops.checkTable(db.getTables()))dbops.createTable(type,db.getStmt(),dbops.getHomeParams());//if not create it
                break;
            case "rooms":
                if(!dbops.checkTable(db.getTables()))dbops.createTable(type,db.getStmt(),dbops.getRoomParams());//if not create it
                break;
            case "objects":
                if(!dbops.checkTable(db.getTables()))dbops.createTable(type,db.getStmt(),dbops.getObjParams());//if not create it
                break;

        }
    }


}