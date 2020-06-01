package mason.db.interfaces;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MyObject 
{
    int id = ThreadLocalRandom.current().nextInt(100000,999999);
    String name = null;
    int count = 0;
    String description = null;
    String location = null;
    String condition = null;
    double price = 0;
    String category = null;
    ArrayList<String> tags = new ArrayList<String>();
}