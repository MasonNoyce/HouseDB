package mason.server;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import mason.servlets.*;


public class MyServer {
    private static MyServer instance = null;

    private int id;
    boolean debug = true;
    private int port = 8080;

    Tomcat server = null;

    private MyServer() {
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

        server.addServlet("/MyHomeDatabase", "ListServlet", new ListServlet()).addMapping("/ListServlet");

        server.addServlet("/MyHomeDatabase", "CreateServlet", new CreateServlet()).addMapping("/CreateServlet");

        server.addServlet("/MyHomeDatabase", "CreateGenServlet", new CreateGenServlet()).addMapping("/CreateGenServlet");

        server.addServlet("/MyHomeDatabase", "DestroyServlet", new DestroyServlet()).addMapping("/DestroyServlet");

        server.addServlet("/MyHomeDatabase", "DestroyGenServlet", new DestroyGenServlet()).addMapping("/DestroyGenServlet");

        server.addServlet("/MyHomeDatabase", "ObjectServlet", new ObjectServlet()).addMapping("/ObjectServlet");

        server.addServlet("/MyHomeDatabase", "UploadServlet", new UploadServlet()).addMapping("/UploadServlet");

        server.addServlet("/MyHomeDatabase", "HomeServlet", new HomeServlet()).addMapping("/HomeServlet");

    }

    
}