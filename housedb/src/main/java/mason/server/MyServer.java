package mason.server;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import mason.servlets.*;
import mason.servlets.createpages.*;
import mason.servlets.destroypages.*;
import mason.servlets.htmlGenerators.*;
import mason.servlets.listpages.*;

public class MyServer {
    private static MyServer instance = null;

    private int id;
    boolean debug = true;
    private int port = 8080;

    Tomcat server = null;

    public MyServer() {
        if (debug == true)
            System.out.println("MyServer Creator Called");

        // fill id;
        id = ThreadLocalRandom.current().nextInt(100000, 999999);

        // Create Tomcat instance
        server = new Tomcat();
        // Set Settings
        server.setBaseDir(new File("target/tomcat/").getAbsolutePath());
        server.setPort(port);
        server.getConnector();

        // Set Webapplications
        server.addWebapp("/MyHomeDatabase", new File("src/main/webapp").getAbsolutePath());

        //Set Servlets
        setServlets();

        // startTomcat
        try {
            System.out.println("Starting Server");
            server.start();
        } catch (LifecycleException e) {
            // TODO Auto-generated catch block
            System.out.println("Unable to start server");
            e.printStackTrace();
        }
        

    }

    public static MyServer getInstance()
    {
        if(instance == null)
        {
            instance = new MyServer();

        }
        return instance;
    }

    public int getID()
    {
        return id;
    }

    private void setServlets()
    {
        server.addServlet("/MyHomeDatabase", "TestServlet", new TestServlet()).addMapping("/TestServlet");

        server.addServlet("/MyHomeDatabase", "ListHomesServlet", new ListHomesServlet()).addMapping("/ListHomesServlet");
        server.addServlet("/MyHomeDatabase", "ListRoomsServlet", new ListRoomsServlet()).addMapping("/ListRoomsServlet");

        server.addServlet("/MyHomeDatabase", "CreateHomeServlet", new CreateHomeServlet()).addMapping("/CreateHomeServlet");
        server.addServlet("/MyHomeDatabase", "CreateRoomServlet", new CreateRoomServlet()).addMapping("/CreateRoomServlet");


        server.addServlet("/MyHomeDatabase", "CreateRoomGenServlet", new CreateRoomGenServlet()).addMapping("/CreateRoomGenServlet");

        server.addServlet("/MyHomeDatabase", "DestroyHomeServlet", new DestroyHomeServlet()).addMapping("/DestroyHomeServlet");
        server.addServlet("/MyHomeDatabase", "DestroyRoomServlet", new DestroyRoomServlet()).addMapping("/DestroyRoomServlet");


        server.addServlet("/MyHomeDatabase", "DestroyHomeGenServlet", new DestroyHomeGenServlet()).addMapping("/DestroyHomeGenServlet");
        server.addServlet("/MyHomeDatabase", "DestroyRoomGenServlet", new DestroyRoomGenServlet()).addMapping("/DestroyRoomGenServlet");

    }

    
}