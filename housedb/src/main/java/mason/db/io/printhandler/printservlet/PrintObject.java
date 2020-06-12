package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mason.db.interfaces.MyObject;

public class PrintObject {
    
    private static PrintObject instance = null;

    public static PrintObject getInstance()
    {
        if(instance == null)
        {
            instance = new PrintObject();
        }
        return instance;
    }

    public void printObject(ArrayList<MyObject> mol, HttpServletResponse resp, PrintWriter out) throws IOException
    {
        //Display List as links in html format
        resp.setContentType("text/html");
        out.println("<a href=\"index.html\">Back</a>");
        
        out.println("<p>Servlet Activated</p>");
        out.println("<p>Entered a fatal program, press return if you want to live</p>");
        //Create links for homes
        
        
        out.println("<p class='text-light'> "+mol.get(0).home + " :" + mol.get(0).room  + " : " 
            + mol.get(0).object + " :" + mol.get(0).id + " :" + mol.get(0).condition
            + " :" + mol.get(0).price+ " :" + mol.get(0).location+ " :" + mol.get(0).category
            + " :" + mol.get(0).description +   " : " + mol.get(0).pic +"</p><br>");
        

        out.println("<img src='images/uploads/"+mol.get(0).home+"/"+mol.get(0).room+"/"+ mol.get(0).pic+"' alt='Girl in a jacket' width='500' height='300'>");
        //out.println("<img src='images/uploads/Mason/gggg/99acb8fe-710d-4786-90a7-75a6fdcf1ff8.jpg' alt='Girl in a jacket' width='500' height='300'>");
        


    }
    
}