package mason.db;

import java.util.concurrent.ThreadLocalRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class MyDatabase {
    private static MyDatabase instance = null;

    private int id;//for testing
    boolean debug = true;

    private Connection c = null;//connection for postgres db




    public MyDatabase()
    {
        if(debug == true)System.out.println("MyDatabase Creator Called");

        //fill id;
        id = ThreadLocalRandom.current().nextInt(100000,999999);

        //Connect to Postgres Database
        dbConnect();

    }

    public static MyDatabase getInstance()
    {
        if(instance == null)
        {
            instance = new MyDatabase();

        }
        return instance;
    }

    public int getID()
    {
        return id;
    }

    private void dbConnect()
    {
        try 
        {
            Class.forName("org.postgresql.Driver");

            c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/myhomedatabase",
            "postgres", "noyce");
            System.out.println("Connected to myhouse");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println("failed to Connect");
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public Connection getConnection()
    {
        return c;
    }

    
    
}