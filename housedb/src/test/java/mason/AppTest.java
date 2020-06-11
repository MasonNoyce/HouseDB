package mason;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import mason.start.DBApp;



/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

/*     @Test
    public void oneDBAppShouldBeCreated()
    {
        DBApp db1 = new DBApp();
        DBApp db2 = new DBApp();

        System.out.println(db1.getID() + "    " + db2.getID());

        assertTrue("Singletons must have same id or they're not singletons", 
        db1.getID()==db2.getID());        

    } */
}
