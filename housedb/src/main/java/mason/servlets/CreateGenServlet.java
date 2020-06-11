package mason.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import mason.db.MyDatabase;
import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;
import mason.db.io.printhandler.PrintHandler;

public class CreateGenServlet extends HttpServlet
{

    private ArrayList<MyHome> mhl;
    private ArrayList<MyRoom> mrl;
    private ArrayList<MyObject> mol;

    private String home;
    private String room;
    private String object;
    private String type;

    Statement stmt = null;


    boolean tableFound;

    MyDatabase db;
    PrintHandler ph;

    String stype = "cgs";

    /*for File Upload*/
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    boolean debug = true;


    
    
    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        db = MyDatabase.getInstance();
        ph = PrintHandler.getInstance();
        filePath = getServletContext().getInitParameter("file-upload"); 


    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //private static final long serialVersionUID = 1L;

        //make connection
        mhl = new ArrayList<>();
        mrl = new ArrayList<>();


        
        //get params
        type = req.getParameter("type");
        home = req.getParameter("home");
        room = req.getParameter("room");



        switch(type)
        {
            case "homes":
                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);

                //ph.printCreateGen(resp);
                break;

            case "rooms":
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
                    if(debug)System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }   
                
                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
                //ph.printCreateGen(resp,home);
                break;

            case "objects":
                try
                {
                    stmt = db.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + type);
                    while(rs.next())
                    {
                        MyRoom mr = new MyRoom();
                        mr.id = rs.getInt("id");
                        mr.home = rs.getString("home");
                        mrl.add(mr);    
                    }
                    if(debug)System.out.println("Finnished grabbing rows from homes");
        
                }
                catch (SQLException e) 
                {
                    System.err.println("Something went wrong!");
                    e.printStackTrace();
                    return;
                }   
                ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);

                //ph.printCreateGen(resp,home,room);
                break;

            default:
                break;
        }


    }
    
}