package mason.servlets.listpages;

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
    String primaryKey;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        type = req.getParameter("type");
        primaryKey = req.getParameter("name");

        switch(type)
        {
            case "homes":
                if(debug)System.out.println("I am in Homes");
                fillStatement();
                if(!dbops.checkTable(tables))dbops.createTable("homes",stmt,dbops.getHomeParams());//if not create it
        
                ArrayList<MyHome> mhl = new ArrayList<>();
                try
                {
                    if(debug)System.out.println("I am trying to grab homes");
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM homes;");
                    while(rs.next())
                    {
                        MyHome mh = new MyHome();
                        mh.id = rs.getInt("id");
                        mh.name = rs.getString("name");
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

                fillStatement();
                if(!dbops.checkTable(tables))dbops.createTable("rooms",stmt,dbops.getRoomParams());//if not create it
                ArrayList<MyRoom> mrl = new ArrayList<>();
                boolean tableFound = false;
                try
                {        
                    stmt = db.getConnection().createStatement();
        
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
        
                    if(rs.next())
                    {
        
                        tableFound = true;
                        do
                        {
        
                            MyRoom mr = new MyRoom();
                            mr.id = rs.getInt("id");
                            mr.name = rs.getString("name");
                            mr.primary = rs.getString("home");
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

                printRooms(resp,mrl,primaryKey,tableFound);
    

                break;

            default:
                break;
        }

    }


    private void fillStatement()
    {
        if(debug)System.out.println("Filling Statements");
        db = new MyDatabase();
        try 
        {
            dbm = db.getConnection().getMetaData();
            tables = dbm.getTables(null,null,type,null);
            stmt = db.getConnection().createStatement();
            if(debug)System.out.println("Statements Filled");
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to connect to metadata");
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
            resp.getWriter().println("<a href=ListServlet?name="+mh.name + "&type=rooms>" + mh.name + "</a><br>");
        }

        out.println("<br><br><a href='pages/createhome.html'>Create Home</a><br><a href='DestroyGenServlet?type=homes'>Destroy Home</a>");

        out.close();        
    }

    private void printRooms(HttpServletResponse resp, ArrayList<MyRoom> mrl,String primaryKey, boolean tableFound)
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
                resp.getWriter().println("<a href=ListRoomsServlet?name="+mr.name + "&type=rooms>" + mr.name + "</a><br>");
            }

            out.println("<br><br><a href='CreateRoomGenServlet?name="+primaryKey+
            "'>Create Room</a><br><a href='DestroyGenServlet?name="+
            primaryKey+"&type=rooms'>Destroy Room</a>");

        }
        else
        {
            out.println("<p> No Rooms Found</p>");
            out.println("<p>Requested Key: " + primaryKey+"</p>");

            out.println("<br><br><a href='CreateRoomGenServlet?name="+primaryKey+
            "'>Create Room</a><br><a href='DestroyGenServlet?name="+
            primaryKey+"&type=rooms'>Destroy Room</a>");

            

        }
        out.close();

    }

}