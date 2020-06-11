package mason.db.io.printhandler.printservlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpRetryException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;
import mason.db.io.printhandler.PrintHandler;

public class PrintResponse 
{
    PrintHandler ph;

    private static PrintResponse instance = null;

    public static PrintResponse getInstance()
    {
        if(instance == null)
        {
            instance = new PrintResponse();
        }
        return instance;
    }

    public void printResponse(HttpServletResponse resp, ArrayList<MyHome> mhl, ArrayList<MyRoom> mrl, ArrayList<MyObject> mol,
    boolean tableFound, String home, String room, String type, String stype) 
    {
        ph = PrintHandler.getInstance();
        String relativePath = "src/main/java/mason/db/io/printhandler/printservlet/htmltemplates/";
        File file = new File(relativePath + "base_generic.html");
        resp.setContentType("text/html");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter out = resp.getWriter();
            String line = br.readLine();

            while(line != null)
            {
                if(line.contains("[HEAD]"))
                {
                    ph.printGen(resp,relativePath + "head.html",out);
                }
                else if(line.contains("[SIDEBAR]"))
                {
                    ph.printGen(resp,relativePath + "sidebar.html",out);
                }
                else if(line.contains("[NAVBAR]"))
                {
                    ph.printGen(resp,relativePath + "navbar.html",out);
                }
                else if(line.contains("[FOOTER]"))
                {
                    System.out.println("printing footer");
                    ph.printGen(resp, relativePath + "footer.html",out);
                }
                else if(line.contains("[CONTENT]"))
                {
                    switch(type)
                    {
                        case "homes":
                            printHomeType(resp,mhl,out,stype);
                            //ph.printList(resp, mhl,out);                           
                            break;

                        case "rooms":
                            printRoomType(resp,mrl,tableFound,home,out,stype);
                            //ph.printList(resp, mrl, tableFound, home,out);
                            break;

                        case "objects":
                           printObjectType(resp,mol,tableFound,home,room,out,stype);
                            //ph.printList(resp, mol, tableFound, home, room,out);
                            break;
                    }
                }
                else
                {
                    out.write(line);
                }
                line = br.readLine();
            }
            out.close();    
        } 
        catch (FileNotFoundException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void printHomeType(HttpServletResponse resp,ArrayList<MyHome> mhl,PrintWriter out,String stype)
    {
        switch(stype)
        {
            case "ls":
                try 
                {
                    ph.printList(resp, mhl, out);
                } 
                catch (IOException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "cgs":
                try {
                    ph.printCreateGen(resp);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "cs":
                try {
                    ph.printCreate(resp, "randomString", out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            
            case "dgs":
                try {
                    ph.printDestroyGen(resp, mhl, out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "ds":
                try {
                    ph.printDestroy(resp,out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "os":
                break;
        }


    }

    private void printRoomType(HttpServletResponse resp,ArrayList<MyRoom>mrl,boolean tableFound, String home, PrintWriter out,String stype)
    {
        switch(stype)
        {
            case "ls":
                try 
                {
                    ph.printList(resp, mrl, tableFound, home, out);
                } 
                catch (IOException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "cgs":
                try {
                    ph.printCreateGen(resp, home);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "cs":
                try {
                    ph.printCreate(resp, home, out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            
            case "dgs":
                try {
                    ph.printDestroyGen(resp, mrl, home,out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "ds":
                try {
                    ph.printDestroy(resp,out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "os":
                break;
        }


    }

    private void printObjectType(HttpServletResponse resp,ArrayList<MyObject>mol,boolean tableFound, String home, String room, PrintWriter out,String stype)
    {
        switch(stype)
        {
            case "ls":
                try 
                {
                    ph.printList(resp, mol, tableFound, home, room,out);
                } 
                catch (IOException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "cgs":
                try {
                    ph.printCreateGen(resp, home, room);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "cs":
                try {
                    ph.printCreate(resp, "another Random String",out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "dgs":
                try {
                    ph.printDestroyGen(resp, mol, home, room,out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "ds":
                try {
                    ph.printDestroy(resp, out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            case "os":
                try {
                    ph.printObject(mol, resp,out);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }
}