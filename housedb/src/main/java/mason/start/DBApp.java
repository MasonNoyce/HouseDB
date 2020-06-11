package mason.start;

import java.util.concurrent.ThreadLocalRandom;

import mason.db.MyDatabase;
import mason.server.MyServer;


public class DBApp {
    private static DBApp instance = null;

    private MyServer myServer = null;
    private int id;
    boolean debug = true;

    private DBApp()
    {
        if(debug == true)System.out.println("DBApp Creator Called");

        //fill id;
        id = ThreadLocalRandom.current().nextInt(100000,999999);

        //Start DB
        MyDatabase.getInstance();
        
        //Start Server
        myServer = MyServer.getInstance();
    }

    public static DBApp getInstance()
    {
        if(instance == null)
        {
            instance = new DBApp();

        }
        return instance;
    }

    public int getID()
    {
        return id;
    }
}