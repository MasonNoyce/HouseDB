package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mason.db.interfaces.MyObject;

public class PrintObject {
    
    public void printObject(ArrayList<MyObject> mol, HttpServletResponse resp) throws IOException
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