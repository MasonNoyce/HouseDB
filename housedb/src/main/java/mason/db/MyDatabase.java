package mason.db;

import java.util.concurrent.ThreadLocalRandom;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase {

    DatabaseMetaData dbm;
    ResultSet tables;
    Statement stmt;

    private static MyDatabase instance = null;

    private int id;//for testing
    boolean debug = true;

    private Connection c = null;//connection for postgres db

    private MyDatabase()
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
    public void fillStatement(String type)
    {
        try 
        {
            dbm = getConnection().getMetaData();
            tables = dbm.getTables(null,null,type,null);
            stmt = getConnection().createStatement();
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to connect to metadata");
        }
    }
    public int getID()
    {
        return id;
    }
    public Statement getStmt()
    {
        return stmt;
    }

    public void setStmt(Statement s)
    {
        stmt = s;
    }

    public ResultSet getTables()
    {
        return tables;
    }
    
}