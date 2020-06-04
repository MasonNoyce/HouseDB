package mason.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mason.db.MyDatabase;
import mason.db.interfaces.MyObject;
import mason.db.io.DBOps;

public class ObjectServlet extends HttpServlet
{
    boolean debug = true;

    MyDatabase db;
    DBOps dbops;
    ArrayList<MyObject> mol;

    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        db = new MyDatabase();
        dbops = new DBOps();
        mol = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        String object = req.getParameter("object");
        String room = req.getParameter("room");
        String home = req.getParameter("home");

        String type = req.getParameter("type");

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

        printObject(mol,resp);

    }

    private void printObject(ArrayList<MyObject> mol, HttpServletResponse resp) throws IOException
    {
                //Display List as links in html format
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<a href=\"index.html\">Back</a>");
                
                resp.getWriter().println("<p>Servlet Activated</p>");
                resp.getWriter().println("<p>Entered a fatal program, press return if you want to live</p>");
                //Create links for homes
                
                
                resp.getWriter().println("<p> "+mol.get(0).home + " :" + mol.get(0).room  + " : " 
                    + mol.get(0).object + " :" + mol.get(0).id + " :" + mol.get(0).condition
                    + " :" + mol.get(0).price+ " :" + mol.get(0).location+ " :" + mol.get(0).category
                    + " :" + mol.get(0).description +   "</p><br>");
                
        
        
                out.close();  

    }
}