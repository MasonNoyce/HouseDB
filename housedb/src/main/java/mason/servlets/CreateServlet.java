package mason.servlets;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    @Override
    public void init() throws ServletException 
    {
        // TODO Auto-generated method stub
        super.init();
        dbops = DBOps.getInstance();
        db = MyDatabase.getInstance();
        ph = PrintHandler.getInstance();

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


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, java.io.IOException 
    {
 

        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(req);


        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
        String fileName = "";
        try 
        { 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(req);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            String fieldName = "";

            String contentType = "";
            boolean isInMemory = false;
            long sizeInBytes = 0;
            FileItem picFile = null;

            while ( i.hasNext () ) 
            {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                    // Get the uploaded file parameters
                    fieldName = fi.getFieldName();
                    fileName = fi.getName();
                    pic = fileName;
                    contentType = fi.getContentType();
                    isInMemory = fi.isInMemory();
                    sizeInBytes = fi.getSize();
                    picFile = fi;
                

                }
                else
                {
                    
                    if(fi.getFieldName().equals("type"))
                        type = fi.getString();
                    if(fi.getFieldName().equals("object"))
                        object = fi.getString();
                    if(fi.getFieldName().equals("room"))
                        room = fi.getString();
                    if(fi.getFieldName().equals("home"))
                        home = fi.getString();

                    if(fi.getFieldName().equals("description"))
                        description = fi.getString();
                    if(fi.getFieldName().equals("location"))
                        location = fi.getString();
                    if(fi.getFieldName().equals("condition"))
                        condition = fi.getString();

                    if(fi.getFieldName().equals("price"))
                        price = fi.getString();
                    if(fi.getFieldName().equals("category"))
                        category = fi.getString();

                }
            }
            if(!fileName.equals(""))
            {
                fileName = UUID.randomUUID().toString();
                System.out.println("******NEW FILE NAME - " + fileName);
                filePath = getServletContext().getInitParameter("file-upload") + "resources/uploads/"+home+"/"+room+"/"; 

                // Write the file
                if( fileName.lastIndexOf("\\") >= 0 ) {
                    file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                } else {
                    file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                }
                picFile.write( file ) ;
            }

        } 
        catch(Exception ex) 
        {
            System.out.println(ex);
        }

        ArrayList<String> valuens = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

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
        pic = fileName;
        values.add("'"+pic+"'");


        System.out.println("*********Type: "+type+"***********");
        
        System.out.println("*********home: "+home+"***********");
        System.out.println("*********room: "+room+"***********");


        //Insert into table
        dbops.insertIntoTable(type, db.getStmt(),valuens,values);


        ph.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);

    }



}