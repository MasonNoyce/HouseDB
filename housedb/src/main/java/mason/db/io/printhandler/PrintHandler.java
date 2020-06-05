package mason.db.io.printhandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;

import mason.db.io.printhandler.printservlet.*;



public class PrintHandler 
{
    boolean debug = true;

    PrintList pl;
    PrintCreateGen pcg;
    PrintCreate pc;
    PrintDestroyGen pdg;
    PrintDestroy pd;
    PrintObject po;
    PrintHtml phtml;
    PrintResponse presp;


    private static PrintHandler instance = null;
    public PrintHandler()
    {
        if(debug)System.out.println("PrintHandlerCalled");
        pl = new PrintList();
        pcg = new PrintCreateGen();
        pc = new PrintCreate();
        pdg = new PrintDestroyGen();
        pd = new PrintDestroy();
        po = new PrintObject();
        phtml = new PrintHtml();
        presp = new PrintResponse();
        
    }


    public static PrintHandler getInstance()
    {
        if(instance == null)
        {
            instance = new PrintHandler();
        }
        return instance;
    }

    //For Home
    public void printList(HttpServletResponse resp, ArrayList<MyHome> mhl, PrintWriter out) throws IOException
    {
        pl.printList(resp, mhl, out);
    }

    //For Home
    public void printList(HttpServletResponse resp, ArrayList<MyRoom> mrl, boolean tableFound, String home, PrintWriter out) throws IOException
    {
            pl.printList(resp, mrl, tableFound, home,out);;
    }

    public void printList(HttpServletResponse resp, ArrayList<MyObject> mol, boolean tableFound, String home, String room, PrintWriter out) throws IOException
    {
        pl.printList(resp, mol, tableFound, home, room,out);
    }

    
    //For Home
    public void printCreateGen(HttpServletResponse resp) throws IOException
    {
        pcg.printCreateGen(resp);
    }

    //For Room
    public void printCreateGen(HttpServletResponse resp, String home) throws IOException
    {
        pcg.printCreateGen(resp, home);
    }

    //For Object
    public void printCreateGen(HttpServletResponse resp, String home, String room) throws IOException
    {
        pcg.printCreateGen(resp, home, room);
    }
    //for homes
    public void printCreate(HttpServletResponse resp, String home, PrintWriter out) throws IOException
    {
        pc.printCreate(resp, home, out);

    }

    //For Rooms
    public void printCreate(HttpServletResponse resp, String home, String room, PrintWriter out) throws IOException
    {
        pc.printCreate(resp, home, room,out);
    }

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyHome> mhl, PrintWriter out) throws IOException
    {
        pdg.printDestroyGen(resp, mhl, out);
    }

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyRoom> mrl, String home, PrintWriter out) throws IOException
    {
        pdg.printDestroyGen(resp, mrl, home, out);
    }

    public void printDestroyGen(HttpServletResponse resp, ArrayList<MyObject> mol, String home,String room, PrintWriter out) throws IOException
    {
        pdg.printDestroyGen(resp, mol, home, room, out);

    }

    public void printDestroy(HttpServletResponse resp, PrintWriter out) throws IOException
    {
        pd.printDestroy(resp, out);  
    }

    public void printObject(ArrayList<MyObject> mol, HttpServletResponse resp) throws IOException
    {

        po.printObject(mol,resp);

    }

    public void printGen(HttpServletResponse resp, String filepath, PrintWriter out)
    {
        phtml.printGen(resp,filepath, out);
    }

    public void printResponse(HttpServletResponse resp, ArrayList<MyHome> mhl, ArrayList<MyRoom> mrl, ArrayList<MyObject> mol,
    boolean tableFound, String home, String room, String type, String stype)
    {
        presp.printResponse(resp, mhl, mrl, mol, tableFound, home, room, type, stype);
    }

}