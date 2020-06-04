package mason.db.interfaces;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MyObject 
{
    public int id = ThreadLocalRandom.current().nextInt(100000,999999);
    public String object = null;
    public String room = null;
    public String home = null;
    public int count = 0;
    public String description = null;
    public String location = null;
    public String condition = null;
    public double price = 0;
    public String category = null;
    public String pic = null;

}