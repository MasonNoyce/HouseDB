package mason.db.io;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBOps 
{
    
    ArrayList<String> homeParams;//For creating home tables
    ArrayList<String> roomParams;//For creating room tables
    ArrayList<String> objParams;
    

    public String test = "I am a test";

    boolean debug = true;

    private static DBOps instance = null;

    public DBOps()
    {
        if(debug == true)System.out.println("DBOps Called");
        //Fill home params gen list
        fillParams();



    }

    public static DBOps getInstance()
    {
        if(instance == null)
        {
            instance = new DBOps();

        }
        return instance;
    }

    private void fillParams()
    {

        homeParams = new ArrayList<>();
        homeParams.add("id serial PRIMARY KEY");
        homeParams.add("home VARCHAR(50) UNIQUE");
        

        roomParams = new ArrayList<>();
        roomParams.add("id serial PRIMARY KEY");
        roomParams.add("room VARCHAR(50) NOT NULL");
        roomParams.add("home VARCHAR(50) REFERENCES homes(home)");

        objParams = new ArrayList<>();
        objParams.add("id serial PRIMARY KEY");
        objParams.add("object VARCHAR(50) NOT NULL");
        objParams.add("room VARCHAR(50) ");
        objParams.add("home VARCHAR(50) REFERENCES homes(home)");
        objParams.add("description VARCHAR(2500)");
        objParams.add("location VARCHAR(200)");
        objParams.add("condition VARCHAR(200)");
        objParams.add("price DECIMAL");
        objParams.add("category VARCHAR(200)");
        objParams.add("pic VARCHAR(200)");//location of pic

        
    }

    public boolean checkTable(ResultSet rs)
    {
        System.out.println("Still Working");

        boolean tableExists = false;
        if(debug)System.out.println("Checking if table exists");
        try 
        {
            if (rs.next()) 
            {
                tableExists = true;
                if (debug)System.out.println("table Exists");
            } 
            else 
            {
                tableExists = false;
                if (debug)System.out.println("table Does not Exists");
            }
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("I Failed in Checktable");
        }



        return tableExists;
    }

    public void createTable(String table, Statement stmt,ArrayList<String> params)
    {
        System.out.println("creating table " + table);
        String createSQL = "CREATE TABLE " + table +"(";
        for(String param : params)
        {
            createSQL = createSQL.concat(param);
            if(params.indexOf(param) != params.size()-1)
            {
                createSQL = createSQL.concat(",");
            }
        }
        createSQL = createSQL.concat(")");

        //id serial PRIMARY KEY, name VARCHAR(50) UNIQUE)
        System.out.println(createSQL);
        try 
        {
            stmt.executeUpdate(createSQL);
//            stmt.close();
        } 
        catch (SQLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("I created table " + table);
        //insert name into homes table
    }

    public void insertIntoTable(String table, Statement stmt, ArrayList<String> valueNames, ArrayList<String> values)
    {        
        String sql = "INSERT INTO " + table + "(";
        for(String valueN : valueNames)
        {
            sql = sql.concat(valueN);
            if(valueNames.indexOf(valueN) != valueNames.size()-1)
            {
                sql = sql.concat(",");
            }
        } 
        sql = sql.concat(") VAlUES (");
        for(String value : values)
        {
            sql = sql.concat(value);
            if(values.indexOf(value) != values.size()-1)
            {
                sql = sql.concat(",");
            }
        }  
        sql = sql.concat(")");
        System.out.println(sql);       
        
        //name) VALUES ('" + name  + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to Execute Query");
        }       
    }

    //For Home
    public void deleteFromTableByName(String table, String toDelete, Statement stmt)
    {
        String sql = "DELETE FROM " + table + " WHERE "+table+".home = '" + toDelete +"'";
        System.out.println(sql);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to Execute Query");
        }     

    }

    //For Room
    public void deleteFromTableByName(String table,String home, String toDelete, Statement stmt)
    {
        String sql = "DELETE FROM " + table + " WHERE "+table+".room = '" + toDelete +"' AND "+ table+".home = '" + home + "'";
        System.out.println(sql);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to Execute Query");
        }     

    }
    //For Object
    public void deleteFromTableByName(String table,String home, String room, String toDelete, Statement stmt)
    {
        String sql = "DELETE FROM " + table + " WHERE "+table+".object = '" + toDelete +"' AND "+ table+".home = '" + home + "' AND "+table+".room = '"+room+"'";
        System.out.println(sql);

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Failed to Execute Query");
        }     

    }


    public ArrayList<String> getRoomParams()
    {
        return roomParams;
    }

    public ArrayList<String> getHomeParams() 
    {
        return homeParams;
    }

    public ArrayList<String> getObjParams()
    {
        return objParams;
    }




}