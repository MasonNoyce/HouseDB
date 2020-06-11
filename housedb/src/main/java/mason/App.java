package mason;

import mason.start.DBApp;
/**
 * Hello world!
 *
 */
public class App 
{
    static boolean debug = true;
    
    public static void main( String[] args )
    {
       
        if (debug==true) System.out.println( "Hello World! Starting App" );
        //Start HouseDB
        DBApp houseDB = DBApp.getInstance();

    }
}
