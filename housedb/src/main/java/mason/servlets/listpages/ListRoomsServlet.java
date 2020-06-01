package mason.servlets.listpages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.interfaces.MyRoom;
import mason.db.io.DBOps;

public class ListRoomsServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private ArrayList<MyRoom> mrl;

    MyDatabase db;
    DatabaseMetaData dbm;
    ResultSet tables;
    Statement stmt = null;
    DBOps dbops;

    boolean debug = true;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        dbops = new DBOps();
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         
        //make connection
        mrl = new ArrayList<>();
        if(debug)System.out.println("TEST");
        if(debug)System.out.println(dbops.test);

        String primaryKey = req.getParameter("name");

        fillStatement();
        if(!dbops.checkTable(tables))dbops.createTable("rooms",stmt,dbops.getRoomParams());//if not create it

        boolean tableFound = false;
        try
        {
            System.out.println("******************************************");

            stmt = db.getConnection().createStatement();
            System.out.println("*******************Connected***********************");

            ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
            System.out.println("***********************ResultSet*******************");

            if(rs.next())
            {
                System.out.println("**********************Started********************");

                tableFound = true;
                do
                {
                    System.out.println("**********************InLoop********************");

                    MyRoom mr = new MyRoom();
                    mr.id = rs.getInt("id");
                    mr.name = rs.getString("name");
                    mr.primary = rs.getString("home");
                    System.out.println("********** " + mr.name + " ***********");
                    mrl.add(mr);    
                }while(rs.next());

                if(debug)System.out.println("Finnished grabbing rows from rooms");
            }
            else
            {
                System.out.println("********************Failed**********************");

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
                resp.getWriter().println("<a href=ListRoomsServlet?name="+mr.name + ">" + mr.name + "</a><br>");
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
    
    private void fillStatement()
    {
        if(debug)System.out.println("Filling Statements");
        db = new MyDatabase();
        try 
        {
            dbm = db.getConnection().getMetaData();
            tables = dbm.getTables(null,null,"rooms",null);
            stmt = db.getConnection().createStatement();
            if(debug)System.out.println("Statements Filled");
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if(debug)System.out.println("Failed to connect to metadata");
        }

    }
}