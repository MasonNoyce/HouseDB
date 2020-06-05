package mason.db.io.printhandler.printservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import mason.db.interfaces.MyHome;
import mason.db.interfaces.MyObject;
import mason.db.interfaces.MyRoom;

public class PrintList {
 
    //For Home
    public void printList(HttpServletResponse resp, ArrayList<MyHome> mhl, PrintWriter out) throws IOException
    {
        //Display List as links in html format
//        resp.setContentType("text/html");
        out.println("<a class='text-light' href=\"index.html\">Back</a>");
        
        resp.getWriter().println("<p class='text-light'>Servlet Activated</p>");
        resp.getWriter().println("<p class='text-light'>Entered a fatal program, press return if you want to live</p>");
        //Create links for homes
        for(MyHome mh : mhl)
        {
            resp.getWriter().println("<a class='text-light' href=ListServlet?home="+mh.home + "&type=rooms>" + mh.home + "</a><br>");
        }

        out.println("<br><br><a class='text-light' href='CreateGenServlet?type=homes'>Create Home</a><br><a href='DestroyGenServlet?type=homes'>Destroy Home</a>");

        System.out.println("***printed home list***");
    }    

    //For Room
    public void printList(HttpServletResponse resp, ArrayList<MyRoom> mrl, boolean tableFound, String home, PrintWriter out) throws IOException
    {
        

        if(tableFound)
        {
            System.out.println("Creating Room Link");

            //Display List as links in html format
            resp.setContentType("text/html");
            out.println("<a href=\"index.html\">Back</a>");
            
            resp.getWriter().println("<p class='text-light'>Servlet Activated</p>");
            resp.getWriter().println("<p class='text-light'>Entered a fatal program, press return if you want to live</p>");
            //Create links for homes
            for(MyRoom mr : mrl)
            {
                System.out.println("Creating Room Link");
                resp.getWriter().println("<a class='text-light' href=ListServlet?room="+mr.room + "&home=" +mr.home+ "&type=objects>" + mr.room + 
                "</a><br>");
            }

            out.println("<br><br><a class='text-light' href='CreateGenServlet?home="+home+
            "&type=rooms'>Create Room</a><br><a class='text-light' href='DestroyGenServlet?home="+
            home+"&type=rooms'>Destroy Room</a>");

        }       
        else
        {
            out.println("<p class='text-light'> No Rooms Found</p>");
            out.println("<p class='text-light'>Requested Key: " + home+"</p>");

            out.println("<br><br><a class='text-light' href='CreateGenServlet?home="+home+
            "&type=rooms'>Create Room</a><br><a class='text-light' href='DestroyGenServlet?home="+
            home+"&type=rooms'>Destroy Room</a>");

            

        }
      
    }

    //For Object
    public void printList(HttpServletResponse resp, ArrayList<MyObject> mol, boolean tableFound, String home, String room, PrintWriter out)
            throws IOException
    {

        if(tableFound)
        {
            System.out.println("Creating Object Link");

            //Display List as links in html format
            resp.setContentType("text/html");
            out.println("<a class='text-light' href=\"index.html\">Back</a>");
            
            resp.getWriter().println("<p class='text-light'>Servlet Activated</p>");
            resp.getWriter().println("<p class='text-light'>Entered a fatal program, press return if you want to live</p>");
            //Create links for homes
            for(MyObject mo : mol)
            {
                System.out.println("Creating ObjectLink Link");

                resp.getWriter().println("<a class='text-light' href=ObjectServlet?object="+mo.object+"&room="+mo.room +
                    "&home=" +mo.home+ "&type=objects>" + mo.object + "</a><br>");
            }

            out.println("<br><br><a class='text-light' href='CreateGenServlet?room="+room+"&home="+home+
            "&type=objects'>Create Object</a><br><a class='text-light' href='DestroyGenServlet?room="+room+"&home="+
            home+"&type=objects'>Destroy Object</a>");

        }       
        else
        {
            out.println("<p class='text-light'> No Objects Found</p>");
            out.println("<p class='text-light'>Requested Key: " + home +"</p>");

            out.println("<br><br><a class='text-light' href='CreateGenServlet?room="+room+"&home="+home+
            "&type=objects'>Create Object</a><br><a class='text-light' href='DestroyGenServlet?room="+room+"home="+
            home+"&type=objects'>Destroy Object</a>");

            

        }

    } 

}